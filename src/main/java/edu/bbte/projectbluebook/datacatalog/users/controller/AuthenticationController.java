package edu.bbte.projectbluebook.datacatalog.users.controller;

import edu.bbte.projectbluebook.datacatalog.users.api.AuthenticationApi;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.TokenInfoRequest;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.TokenInfoResponse;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.UserLoginRequest;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.UserLoginResponse;
import edu.bbte.projectbluebook.datacatalog.users.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class AuthenticationController implements AuthenticationApi {
    @Autowired
    private AuthenticationService service;

    @Override
    public Mono<ResponseEntity<UserLoginResponse>> login(@Valid Mono<UserLoginRequest> userLoginRequest,
                                                         ServerWebExchange exchange) {
        return service
                .login(userLoginRequest)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<TokenInfoResponse>> tokenInfo(@Valid Mono<TokenInfoRequest> tokenInfoRequest,
                                                             ServerWebExchange exchange) {
        return service
                .decodeToken(tokenInfoRequest)
                .map(ResponseEntity::ok);
    }
}
