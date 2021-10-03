package edu.bbte.projectbluebook.datacatalog.users.repository;

import edu.bbte.projectbluebook.datacatalog.users.model.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByUsername(String username);

    Flux<User> findAllByUsernameContainingIgnoreCase(String username);

    Flux<User> findAllByIdIn(List<String> ids);

    @Query("{ keys: { $elemMatch: { key: ?0 } } }")
    Mono<User> findByApiKey(String key);
}
