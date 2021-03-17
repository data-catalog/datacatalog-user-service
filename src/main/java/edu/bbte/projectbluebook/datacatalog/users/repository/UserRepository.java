package edu.bbte.projectbluebook.datacatalog.users.repository;

import edu.bbte.projectbluebook.datacatalog.users.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByUsername(String username);
}
