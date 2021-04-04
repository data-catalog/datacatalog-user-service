package edu.bbte.projectbluebook.datacatalog.users.service;

import com.mongodb.DuplicateKeyException;
import edu.bbte.projectbluebook.datacatalog.users.exception.NotFoundException;
import edu.bbte.projectbluebook.datacatalog.users.exception.UserServiceException;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.UserCreationRequest;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.UserResponse;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.UserRoleUpdateRequest;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.UserUpdateRequest;
import edu.bbte.projectbluebook.datacatalog.users.model.mapper.UserMapper;
import edu.bbte.projectbluebook.datacatalog.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    UserMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Mono<UserResponse> createUser(Mono<UserCreationRequest> userCreationRequest) {
        return userCreationRequest
                .map(request -> request.password(passwordEncoder.encode(request.getPassword())))
                .map(request -> mapper.requestDtoToModel(request))
                .flatMap(user -> repository.insert(user))
                .map(user -> mapper.modelToResponseDto(user))
                .onErrorMap(DuplicateKeyException.class, err ->
                        new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                                "Username or e-mail address is already used."))
                .onErrorMap(err -> new UserServiceException("User could not be created."));
    }

    public Mono<Void> deleteUser(String userId) {
        return repository
                .deleteById(userId)
                .onErrorMap(err -> new UserServiceException("User could not be deleted."));
    }

    public Mono<UserResponse> getUser(String userId) {
        return repository
                .findById(userId)
                .map(mapper::modelToResponseDto)
                .onErrorMap(err -> new UserServiceException("User could not be retrieved."))
                .switchIfEmpty(Mono.error(new NotFoundException("User not found.")));
    }

    public Flux<UserResponse> getUsers() {
        return repository.findAll()
                .map(mapper::modelToResponseDto)
                .onErrorMap(err -> new UserServiceException("Users could not be retrieved."));
    }

    public Flux<UserResponse> getManyUsersByIds(List<String> ids) {
        return repository.findAllByIdIn(ids)
                .map(mapper::modelToResponseDto)
                .onErrorMap(err -> new UserServiceException("Users could not be retrieved."));
    }

    public Mono<UserResponse> getUserByUsername(String username) {
        return repository
                .findByUsername(username)
                .map(mapper::modelToResponseDto)
                .onErrorMap(err -> new UserServiceException("User could not be retrieved."))
                .switchIfEmpty(Mono.error(new NotFoundException("User not found.")));
    }

    public Flux<UserResponse> searchUsers(String searchTerm) {
        return repository
                .findAllByUsernameContainingIgnoreCase(searchTerm)
                .map(mapper::modelToResponseDto)
                .onErrorMap(err -> new UserServiceException("User could not be retrieved."));
    }

    public Mono<Void> updateUser(String userId, Mono<UserUpdateRequest> userRequest) {
        Mono<UserUpdateRequest> requestMono = userRequest
                .map(request -> {
                    String password = request.getPassword();
                    if (password != null) {
                        request.setPassword(passwordEncoder.encode(password));
                    }

                    return request;
                });

        return repository
                .findById(userId)
                .switchIfEmpty(Mono.error(new NotFoundException("User not found.")))
                .zipWith(requestMono)
                .map(tuple -> mapper.updateModelFromDto(tuple.getT1(), tuple.getT2()))
                .flatMap(repository::save)
                .then()
                .onErrorMap(err -> new UserServiceException("User could not be updated."));
    }

    public Mono<Void> modifyUserRole(String userId, Mono<UserRoleUpdateRequest> userRoleUpdateRequest) {
        Mono<String> roleMono = userRoleUpdateRequest.map(request -> request.getRole().toString());

        return repository.findById(userId)
                .switchIfEmpty(Mono.error(new NotFoundException("User not found.")))
                .zipWith(roleMono)
                .map(tuple -> {
                    tuple.getT1().setRole(tuple.getT2());
                    return tuple.getT1();
                })
                .flatMap(repository::save)
                .then()
                .onErrorMap(err -> new UserServiceException("User's role could not be updated."));
    }
}
