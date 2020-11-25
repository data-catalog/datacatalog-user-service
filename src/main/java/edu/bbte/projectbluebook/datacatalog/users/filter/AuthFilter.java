package edu.bbte.projectbluebook.datacatalog.users.filter;

import edu.bbte.projectbluebook.datacatalog.users.model.TokenValidation;
import edu.bbte.projectbluebook.datacatalog.users.util.JwtUtil;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter implements Filter {

    private final JwtUtil jwtUtil = new JwtUtil();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("DOFILTER");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String url = httpRequest.getRequestURL().toString();
        String method = httpRequest.getMethod();

        // Check for login / register endpoints
        if ("POST".equals(method.toUpperCase())) {
            if (url.endsWith("/users") || url.endsWith("/users/login")) {
                chain.doFilter(httpRequest, httpServletResponse);
                return;
            }
        }

        // Check authorization header
        String authorization = httpRequest.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            httpServletResponse.setStatus(403);
            httpServletResponse.getWriter().write("JWT Token doesn't exist");
            return;
        }

        // Check token validity and retrieve relevant info
        String authToken = authorization.substring(7);
        TokenValidation tokenValidation = jwtUtil.validateToken(authToken);
        if (tokenValidation == null) {
            httpServletResponse.setStatus(403);
            httpServletResponse.getWriter().write("JWT Token doesn't exist");
            return;
        }

        // Check possible API endpoints
        if ("DELETE".equals(method.toUpperCase())) {
            int role = tokenValidation.roleid();
            System.out.println("DELETE " + role);
            if (role >= 3) {
                httpServletResponse.setStatus(401);
                httpServletResponse.getWriter().write("Unauthorized");
            }
            else {
                chain.doFilter(httpRequest, httpServletResponse);
            }
        }

    }
}
