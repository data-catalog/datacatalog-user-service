package edu.bbte.projectbluebook.datacatalog.users.service;

import edu.bbte.projectbluebook.datacatalog.users.model.dto.TokenInfoRequest;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.TokenInfoResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthenticationServiceTest {

    @Autowired
    AuthenticationService authenticationService;

    @Test
    void decodeToken_valid() {
        final String secret = "e87ad8981f954e17a622b8663db8520c";

        Claims claims = Jwts.claims()
                .setSubject("testusername")
                .setIssuedAt(new Date(System.currentTimeMillis() - 10000))
                .setExpiration(new Date(System.currentTimeMillis() + 10000));

        claims.put("userId", "testid");
        claims.put("role", "user");

        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        TokenInfoRequest request = new TokenInfoRequest().token(token);

        TokenInfoResponse response = authenticationService.decodeToken(Mono.just(request)).block();

        assertNotNull(response);
        assertTrue(response.getActive());
        assertEquals("testid", response.getUserId());
    }

    @Test
    void decodeToken_invalid() {
        final String secret = "invalid secret";

        Claims claims = Jwts.claims()
                .setSubject("testusername")
                .setIssuedAt(new Date(System.currentTimeMillis() - 10000))
                .setExpiration(new Date(System.currentTimeMillis() + 10000));

        claims.put("userId", "testid");
        claims.put("role", "user");

        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        TokenInfoRequest request = new TokenInfoRequest().token(token);

        TokenInfoResponse response = authenticationService.decodeToken(Mono.just(request)).block();

        assertNotNull(response);
        assertFalse(response.getActive());
    }

    @Test
    void decodeToken_expired() {
        final String secret = "e87ad8981f954e17a622b8663db8520c";

        Claims claims = Jwts.claims()
                .setSubject("testusername")
                .setIssuedAt(new Date(System.currentTimeMillis() - 10000))
                .setExpiration(new Date(System.currentTimeMillis() - 5000));

        claims.put("userId", "testid");
        claims.put("role", "user");

        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        TokenInfoRequest request = new TokenInfoRequest().token(token);

        TokenInfoResponse response = authenticationService.decodeToken(Mono.just(request)).block();

        assertNotNull(response);
        assertFalse(response.getActive());
    }
}