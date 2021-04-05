package edu.bbte.projectbluebook.datacatalog.users.service;

import edu.bbte.projectbluebook.datacatalog.users.model.User;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.TokenInfoRequest;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.TokenInfoResponse;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.UserLoginRequest;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.UserLoginResponse;
import edu.bbte.projectbluebook.datacatalog.users.model.mapper.UserMapper;
import edu.bbte.projectbluebook.datacatalog.users.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
public class AuthenticationService {
    // FIXME: remove hardcoded secret
    private static final String SECRET = "e87ad8981f954e17a622b8663db8520c";

    private JwtParser jwtParser;

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void postConstruct() {
        jwtParser = Jwts.parser().setSigningKey(SECRET);
    }

    public Mono<UserLoginResponse> login(Mono<UserLoginRequest> userLoginRequest) {
        return userLoginRequest
                .flatMap(request -> repository
                        .findByUsername(request.getUsername())
                        .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword())))
                .map(user -> mapper.modelToLoginResponse(user, createToken(user)))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));
    }

    private String createToken(User user) {
        Claims claims = Jwts.claims()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 172800000));

        claims.put("userId", user.getId());
        claims.put("role", user.getRole());

        // "sub" claim already set, should remove this if clients don't use it
        claims.put("username", user.getUsername());

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public Mono<TokenInfoResponse> decodeToken(Mono<TokenInfoRequest> tokenInfoRequest) {
        return tokenInfoRequest
                .map(request -> jwtParser.parseClaimsJws(request.getToken()).getBody())
                .map(claims -> new TokenInfoResponse()
                        .active(true)
                        .exp(claims.getExpiration().getTime())
                        .iat(claims.getIssuedAt().getTime())
                        .userId(claims.get("userId", String.class))
                        .username(claims.getSubject())
                        .role(TokenInfoResponse.RoleEnum.fromValue(claims.get("role", String.class))))
                .onErrorReturn(new TokenInfoResponse().active(false));
    }
}
