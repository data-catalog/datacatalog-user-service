package edu.bbte.projectbluebook.datacatalog.users.controller;

import edu.bbte.projectbluebook.datacatalog.users.api.UserApi;
import edu.bbte.projectbluebook.datacatalog.users.model.*;
import edu.bbte.projectbluebook.datacatalog.users.service.UserMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserMongoController implements UserApi {

    @Autowired
    private UserMongoService service;

    @Override
    public ResponseEntity<Void> createUser(@Valid UserRequest userRequest) {
        return service.createUser(userRequest);
    }

    @Override
    public ResponseEntity<UserLoginResponse> login(@Valid UserLoginRequest userLoginRequest) {
        return service.login(userLoginRequest);
    }

    @Override
    public ResponseEntity<Void> deleteUser(String userId) {
        return service.deleteUser(userId);
    }

    @Override
    public ResponseEntity<UserResponse> getUser(String userId) {
        return service.getUser(userId);
    }

    @Override
    public ResponseEntity<List<UserResponse>> getUsers() {
        return service.getUsers();
    }

    @Override
    public ResponseEntity<TokenInfoResponse> tokenInfo(@Valid String body) {
        return service.tokenInfo(body);
    }
}
