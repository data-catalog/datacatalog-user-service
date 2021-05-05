package edu.bbte.projectbluebook.datacatalog.users.controller;

import edu.bbte.projectbluebook.datacatalog.users.api.ApiKeyApi;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.ApiKeyCreationRequest;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.ApiKeyCreationResponse;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.ApiKeyResponse;
import edu.bbte.projectbluebook.datacatalog.users.service.ApiKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
public class ApiKeyController implements ApiKeyApi {
    @Autowired
    ApiKeyService service;

    @Override
    public Mono<ResponseEntity<ApiKeyCreationResponse>> createUserApiKey(
            Mono<ApiKeyCreationRequest> apiKeyCreationRequest,
            ServerWebExchange exchange) {

        return exchange.getPrincipal().map(Principal::getName)
                .flatMap(userId -> service.createApiKey(userId, apiKeyCreationRequest))
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteUserApiKey(String keyId, ServerWebExchange exchange) {
        return exchange.getPrincipal().map(Principal::getName)
                .flatMap(userId -> service.deleteApiKey(userId, keyId))
                .map(nothing -> ResponseEntity.noContent().build());
    }

    @Override
    public Mono<ResponseEntity<ApiKeyResponse>> getUserApiKey(String keyId, ServerWebExchange exchange) {
        return exchange.getPrincipal().map(Principal::getName)
                .flatMap(userId -> service.getUserApiKeyById(userId, keyId))
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Flux<ApiKeyResponse>>> getUserApiKeys(ServerWebExchange exchange) {
        Flux<ApiKeyResponse> response = exchange.getPrincipal().map(Principal::getName)
                .flatMapMany(userId -> service.getUserApiKeys(userId));

        return Mono.just(response).map(ResponseEntity::ok);
    }
}
