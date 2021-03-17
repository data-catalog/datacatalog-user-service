package edu.bbte.projectbluebook.datacatalog.users.config.security;

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
import reactor.core.publisher.Mono;

@Configuration
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
public class SecurityConfiguration {

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
                .pathMatchers(HttpMethod.DELETE, "/users/{userId}")
                    .access((authentication, context) -> isAdmin(authentication))
                .pathMatchers("/user/keys/**").authenticated()
                .anyExchange().permitAll()
                .and()
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    private Mono<AuthorizationDecision> isAdmin(Mono<Authentication> authentication) {
        return authentication
                .map(Authentication::getAuthorities)
                .map(authorities -> authorities.stream()
                        .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN")))
                .map(AuthorizationDecision::new);
    }
}
