package edu.bbte.projectbluebook.datacatalog.users.controller;

import edu.bbte.projectbluebook.datacatalog.users.api.UserApi;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.UserCreationRequest;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.UserResponse;
import edu.bbte.projectbluebook.datacatalog.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class UserController implements UserApi {
    @Autowired
    private UserService service;

    @Override
    public Mono<ResponseEntity<Void>> createUser(@Valid Mono<UserCreationRequest> userCreationRequest,
                                                 ServerWebExchange exchange) {
        UriComponentsBuilder locationBuilder = UriComponentsBuilder
                .fromUri(exchange.getRequest().getURI())
                .path("/{id}");

        return service
                .createUser(userCreationRequest)
                .map(userResponse -> locationBuilder.buildAndExpand(userResponse.getId()).toUri())
                .map(location -> ResponseEntity.created(location).build());
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteUser(String userId, ServerWebExchange exchange) {
        return service
                .deleteUser(userId)
                .map(nothing -> ResponseEntity.noContent().build());
    }

    @Override
    public Mono<ResponseEntity<UserResponse>> getUser(String userId, ServerWebExchange exchange) {
        return service
                .getUser(userId)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Flux<UserResponse>>> getUsers(ServerWebExchange exchange) {
        return Mono.just(service.getUsers())
                .map(ResponseEntity::ok);
    }
}
