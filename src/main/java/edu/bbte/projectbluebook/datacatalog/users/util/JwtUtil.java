package edu.bbte.projectbluebook.datacatalog.users.util;

import edu.bbte.projectbluebook.datacatalog.users.model.TokenInfoResponse;
import edu.bbte.projectbluebook.datacatalog.users.model.UserResponse;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JwtUtil {

    private static final String SECRET = "e87ad8981f954e17a622b8663db8520c";

    public String extractUsername(Claims claims) {
        return claims.get("username").toString();
    }

    public Date extractExpiration(Claims claims) {
        return claims.getExpiration();
    }

    public String extractRole(Claims claims) {
        return claims.get("role").toString();
    }

    public String extractUserId(Claims claims) {
        return claims.get("userId").toString();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
            .setSigningKey(SECRET)
            .parseClaimsJws(token)
            .getBody();
    }

    private Boolean isTokenExpired(Date expDate) {
        return expDate.before(new Date());
    }

    public String generateToken(UserResponse userResponse) {
        Map<String, Object> claims = new ConcurrentHashMap<>();
        claims.put("role", userResponse.getRole());
        claims.put("username", userResponse.getUsername());
        claims.put("userId", userResponse.getId());
        return createToken(claims);
    }

    private String createToken(Map<String,Object> claims) {
        return Jwts.builder()
            .setClaims(claims)
            .setHeaderParam("typ", "JWT")
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 172800000))
            .signWith(SignatureAlgorithm.HS256, SECRET).compact();
    }

    public TokenInfoResponse validateToken(String token) {
        TokenInfoResponse response = new TokenInfoResponse();
        try {
            final Claims claims = extractAllClaims(token);
            Date expirationDate = extractExpiration(claims);
            String role = extractRole(claims);
            response.setExp((int) expirationDate.getTime());
            response.setIat((int)claims.getIssuedAt().getTime());

            if ("user".equals(role)) {
                response.setRole(TokenInfoResponse.RoleEnum.USER);
            } else if ("admin".equals(role)) {
                response.setRole(TokenInfoResponse.RoleEnum.ADMIN);
            } else {
                throw new JwtException("Unkown role");
            }
            response.setActive(!isTokenExpired(expirationDate));
            response.setUserId(extractUserId(claims));
            response.setUsername(extractUsername(claims));
            return response;
        } catch (JwtException ex) {
            response.setActive(false);
            return response;
        }
    }

}