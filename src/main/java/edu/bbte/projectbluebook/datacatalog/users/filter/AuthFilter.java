package edu.bbte.projectbluebook.datacatalog.users.filter;

import edu.bbte.projectbluebook.datacatalog.users.model.TokenInfoResponse;
import edu.bbte.projectbluebook.datacatalog.users.util.JwtUtil;
import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Component
public class AuthFilter implements Filter {

    private final JwtUtil jwtUtil = new JwtUtil();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String uri = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        // Check for login / register endpoints
        if (isRegisterOrLogin(method, uri)) {
            chain.doFilter(httpRequest, httpServletResponse);
            return;
        }

        // Check authorization header
        String authorization = httpRequest.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            sendResponse(403,"JWT Token is invalid", httpServletResponse);
            return;
        }

        // Check token validity and retrieve relevant info
        String authToken = authorization.substring(7);
        TokenInfoResponse tokenValidation = jwtUtil.validateToken(authToken);
        if (!tokenValidation.getActive()) {
            sendResponse(403,"JWT Token is not active", httpServletResponse);
            return;
        }

        // Check possible API endpoints
        // DELETE endpoint(s)
        TokenInfoResponse.RoleEnum role = tokenValidation.getRole();
        if ("DELETE".equals(method.toUpperCase(new Locale("en", "US")))) {
            if (role == TokenInfoResponse.RoleEnum.ADMIN) {
                chain.doFilter(httpRequest, httpServletResponse);
            } else {
                sendResponse(401,"Unauthorized", httpServletResponse);
            }
            return;
        }

        // GET /users and GET users/{userId}
        if ("GET".equals(method.toUpperCase(new Locale("en", "US"))) && uri.startsWith("/users")) {
            if (role == TokenInfoResponse.RoleEnum.ADMIN) {
                chain.doFilter(httpRequest, httpServletResponse);
            } else {
                sendResponse(401,"Unauthorized", httpServletResponse);
            }
            return;
        }
        chain.doFilter(httpRequest, httpServletResponse);
    }

    private void sendResponse(int statusCode, String message, HttpServletResponse httpServletResponse)
            throws IOException {
        httpServletResponse.setStatus(statusCode);
        httpServletResponse.getWriter().write(message);
    }

    private boolean isRegisterOrLogin(String method, String uri) {
        return "POST".equals(method.toUpperCase(new Locale("en", "US"))) && uri.startsWith("/users");
    }

}
