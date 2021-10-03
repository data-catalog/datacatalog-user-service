package edu.bbte.projectbluebook.datacatalog.users.config.security;

import edu.bbte.projectbluebook.datacatalog.users.model.dto.TokenInfoRequest;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.TokenInfoResponse;
import edu.bbte.projectbluebook.datacatalog.users.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        TokenInfoRequest request = new TokenInfoRequest().token(authentication.getCredentials().toString());

        return authenticationService.decodeToken(Mono.just(request))
                .filter(TokenInfoResponse::getActive)
                .switchIfEmpty(Mono.error(new BadCredentialsException("The JWT token is invalid or expired.")))
                .map(response -> new UsernamePasswordAuthenticationToken(
                        response.getUserId(),
                        authentication.getCredentials(),
                        createAuthorities(response.getRole())))
                .cast(Authentication.class);
    }

    private Collection<GrantedAuthority> createAuthorities(TokenInfoResponse.RoleEnum role) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.getValue().toUpperCase(Locale.ENGLISH)));
    }
}
