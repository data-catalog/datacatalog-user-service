package edu.bbte.projectbluebook.datacatalog.users.util;

import edu.bbte.projectbluebook.datacatalog.users.model.TokenValidation;
import edu.bbte.projectbluebook.datacatalog.users.model.UserResponse;
import io.jsonwebtoken.SignatureException;
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

    public TokenValidation validateToken(String token) {
        try {
            final Claims claims = extractAllClaims(token);
            Date expirationDate = extractExpiration(claims);
            if (isTokenExpired(expirationDate)) {
                return null;
            } else {
                return new TokenValidation(extractUsername(claims), extractRole(claims));
            }

        } catch (SignatureException e) {
            return null;
        }
    }

}