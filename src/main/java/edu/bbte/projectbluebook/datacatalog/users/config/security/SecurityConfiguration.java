package edu.bbte.projectbluebook.datacatalog.users.config.security;

import edu.bbte.projectbluebook.datacatalog.users.exception.NotFoundException;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.UserResponse;
import edu.bbte.projectbluebook.datacatalog.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

@Configuration
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
public class SecurityConfiguration {
    @Autowired
    UserService userService;

    @Autowired
    JwtAuthenticationManager authenticationManager;

    @Autowired
    JwtServerAuthenticationConverter authenticationConverter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(authenticationConverter);

        return http
                .cors().disable()
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .httpBasic().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.PATCH, "/users/{userId}").access(this::isSelfOrAdmin)
                .pathMatchers(HttpMethod.PUT, "/users/{userId}/role")
                    .access((authentication, context) -> isAdmin(authentication))
                .pathMatchers(HttpMethod.DELETE, "/users/{userId}")
                    .access((authentication, context) -> isAdmin(authentication))
                .pathMatchers("/user/keys/**").authenticated()
                .anyExchange().permitAll()
                .and()
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    private Mono<AuthorizationDecision> isSelf(Mono<Authentication> authentication,
                                                         AuthorizationContext context) {
        Mono<UserResponse> userResponse = userService
                .getUser(context.getVariables().get("userId").toString())
                .switchIfEmpty(Mono.error(new NotFoundException("User not found.")));

        Mono<String> principal = authentication.map(Authentication::getPrincipal).cast(String.class).defaultIfEmpty("");

        return userResponse.zipWith(principal)
                .map(tuple -> tuple.getT1().getId().equals(tuple.getT2()))
                .defaultIfEmpty(false)
                .map(AuthorizationDecision::new);
    }

    private Mono<AuthorizationDecision> isAdmin(Mono<Authentication> authentication) {
        return authentication
                .map(Authentication::getAuthorities)
                .map(authorities -> authorities.stream()
                        .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN")))
                .defaultIfEmpty(false)
                .map(AuthorizationDecision::new);
    }

    private Mono<AuthorizationDecision> isSelfOrAdmin(Mono<Authentication> authentication,
                                                        AuthorizationContext context) {
        return isAdmin(authentication)
                .zipWith(isSelf(authentication, context))
                .map(tuple -> tuple.getT1().isGranted() || tuple.getT2().isGranted())
                .map(AuthorizationDecision::new);
    }
}
