package edu.bbte.projectbluebook.datacatalog.users.service;

import edu.bbte.projectbluebook.datacatalog.users.exception.NotFoundException;
import edu.bbte.projectbluebook.datacatalog.users.model.ApiKey;
import edu.bbte.projectbluebook.datacatalog.users.model.User;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.ApiKeyCreationRequest;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.ApiKeyCreationResponse;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.ApiKeyResponse;
import edu.bbte.projectbluebook.datacatalog.users.model.mapper.ApiKeyMapper;
import edu.bbte.projectbluebook.datacatalog.users.repository.UserRepository;
import org.bson.internal.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiKeyService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private ApiKeyMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Mono<ApiKeyCreationResponse> createApiKey(String userId, Mono<ApiKeyCreationRequest> apiKeyCreationRequest) {
        String key = Base64.encode(KeyGenerators.secureRandom(64).generateKey());

        Mono<ApiKey> apiKeyMono = apiKeyCreationRequest
                .map(request -> mapper.requestDtoToModel(request))
                .map(apiKey -> apiKey.setKey(passwordEncoder.encode(key)));

        return repository
                .findById(userId)
                .zipWith(apiKeyMono)
                .filter(tuple -> tuple.getT1()
                        .getKeys().stream()
                        .noneMatch(apiKey -> apiKey.getTitle().equals(tuple.getT2().getTitle())))
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.UNPROCESSABLE_ENTITY, "API key with this title already exists.")))
                .flatMap(tuple -> {
                    tuple.getT1().getKeys().add(tuple.getT2());
                    Mono<User> saveMono = repository.save(tuple.getT1());
                    return Mono.zip(saveMono, Mono.just(tuple.getT2()));
                })
                .map(Tuple2::getT2)
                .map(apiKey -> mapper.modelToCreationResponseDto(apiKey));
    }

    public Mono<Void> deleteApiKey(String userId, String keyId) {
        return repository
                .findById(userId)
                .flatMap(user -> {
                    List<ApiKey> newKeys = user.getKeys().stream()
                            .filter(apiKey -> !apiKey.getId().equals(keyId)).collect(Collectors.toList());

                    user.setKeys(newKeys);
                    return repository.save(user);
                })
                .then();
    }

    public Flux<ApiKeyResponse> getUserApiKeys(String userId) {
        return repository
                .findById(userId)
                .flatMapMany(user -> Flux.fromIterable(user.getKeys()))
                .map(apiKey -> mapper.modelToResponseDto(apiKey));
    }

    public Mono<ApiKeyResponse> getUserApiKeyById(String userId, String keyId) {
        return repository
                .findById(userId)
                .map(user -> user.getKeys().stream().filter(apiKey -> apiKey.getId().equals(keyId)).findFirst())
                .flatMap(optionalApiKey -> optionalApiKey.map(Mono::just).orElseGet(Mono::empty))
                .map(apiKey -> mapper.modelToResponseDto(apiKey))
                .switchIfEmpty(Mono.error(new NotFoundException("Api key not found.")));
    }
}
