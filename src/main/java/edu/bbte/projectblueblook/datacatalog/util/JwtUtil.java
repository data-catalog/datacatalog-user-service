package edu.bbte.projectblueblook.datacatalog.util;

import edu.bbte.projectblueblook.datacatalog.model.UserBase;
import edu.bbte.projectblueblook.datacatalog.model.UserResponse;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
        return createToken(claims, userResponse);
    }

    private String createToken(Map<String,Object> claims, UserResponse userResponse) {
        return Jwts.builder()
            .setClaims(claims)
            .setHeaderParam("typ", "JWT")
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 172800000))
            .signWith(SignatureAlgorithm.HS256, SECRET).compact();
    }


    public Boolean validateToken(String token, UserResponse userResponse) {
        final Claims claims = extractAllClaims(token);
        String username = extractUsername(claims);
        String userId = extractUserId(claims);
        String role = extractRole(claims);
        Date expirationDate = extractExpiration(claims);

        return username.equals(userResponse.getUsername())
            && role.equals(userResponse.getRole().toString())
            && userId.equals(userResponse.getId())
            && !isTokenExpired(expirationDate);
    }

}
