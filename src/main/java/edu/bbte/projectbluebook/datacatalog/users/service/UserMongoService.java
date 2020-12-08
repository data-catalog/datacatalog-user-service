package edu.bbte.projectbluebook.datacatalog.users.service;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import edu.bbte.projectbluebook.datacatalog.users.model.*;
import edu.bbte.projectbluebook.datacatalog.users.repository.UserMongoRepository;
import edu.bbte.projectbluebook.datacatalog.users.util.JwtUtil;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserMongoService {

    @Autowired
    private UserMongoRepository repository;

    private final JwtUtil jwtUtil = new JwtUtil();
    private final SecureRandom secureRandom = new SecureRandom();
    private final PasswordEncoder passwordencoder = new BCryptPasswordEncoder(10, secureRandom);

    public ResponseEntity<Void> createUser(@Valid UserRequest userRequest) {
        String email = userRequest.getEmail();
        // Checking if email is unique
        Document emailDocument = new Document("email", email);
        Document userEmail = repository.findByFilter(emailDocument)
            .first();
        if (userEmail != null) {
            return new ResponseEntity<Void>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        // Checking if username is unique
        String username = userRequest.getUsername();
        Document usernameDocument = new Document("username", username);
        Document userName = repository.findByFilter(usernameDocument)
            .first();
        if (userName != null) {
            return new ResponseEntity<Void>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        // If email and username are unique then register the new user
        // hash + salt password with BCrypt
        String hashAndSalt = passwordencoder.encode(userRequest.getPassword());
        Document user = new Document();
        user.append("email", email);
        user.append("username", username);
        user.append("first_name", userRequest.getFirstName());
        user.append("last_name", userRequest.getLastName());
        user.append("role", userRequest.getRole().toString());
        user.append("password", hashAndSalt);

        return repository.insert(user)
            ? new ResponseEntity<>(HttpStatus.CREATED)
            : new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public ResponseEntity<UserLoginResponse> login(@Valid UserLoginRequest userLoginRequest) {
        String username = userLoginRequest.getUsername();

        Document user = repository.findByFilter(new Document("username", username))
            .first();
        if (user == null) {
            return new ResponseEntity<UserLoginResponse>(HttpStatus.UNAUTHORIZED);
        }
        String password = userLoginRequest.getPassword();
        String hashed = user.get("password").toString();
        if (passwordencoder.matches(password, hashed)) {
            // ADD USER TO RESPONSE
            UserResponse userResponse = new UserResponse();
            userResponse.setUsername(username);
            userResponse.setId(user.getObjectId("_id").toString());
            userResponse.setEmail(user.getString("email"));
            userResponse.setLastName(user.getString("last_name"));
            userResponse.setFirstName(user.getString("first_name"));
            userResponse.setRole(user.getString("role").equals("admin")
                ? UserResponse.RoleEnum.ADMIN
                : UserResponse.RoleEnum.USER
            );
            UserLoginResponse response = new UserLoginResponse();
            response.setUser(userResponse);

            // SET JWT TOKEN HERE
            String token = jwtUtil.generateToken(userResponse);
            response.setToken(token);
            return new ResponseEntity<UserLoginResponse>(response, HttpStatus.OK);
        }
        return new ResponseEntity<UserLoginResponse>(HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<Void> deleteUser(String userId) {
        Document deleted;
        try {
             deleted = repository.delete(new Document("_id", userId));
        } catch (MongoException e) {
            // TODO: Implement logging
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (deleted == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<UserResponse> getUser(String userId) {
        Document user;
        try {
            user = repository.findByFilter(new Document("_id", new ObjectId(userId)))
                .first();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<UserResponse>(HttpStatus.NOT_FOUND);
        }
        if (user == null) {
            return new ResponseEntity<UserResponse>(HttpStatus.NOT_FOUND);
        }
        UserResponse result = new UserResponse();
        result.setId(userId);
        result.setEmail(user.get("email").toString());
        result.setFirstName(user.get("first_name").toString());
        result.setLastName(user.get("last_name").toString());
        result.setUsername(user.get("username").toString());
        result.setRole(user.get("role").toString().equals("admin")
            ? UserResponse.RoleEnum.ADMIN
            : UserResponse.RoleEnum.USER);
        return new ResponseEntity<UserResponse>(result, HttpStatus.OK);
    }

    public ResponseEntity<List<UserResponse>> getUsers() {
        FindIterable<Document> docs = repository.findAll();
        List<UserResponse> results = new ArrayList<>();
        for (Document doc : docs) {
            UserResponse result = new UserResponse();
            result.setId(doc.get("_id").toString());
            result.setEmail(doc.get("email").toString());
            result.setFirstName(doc.get("first_name").toString());
            result.setLastName(doc.get("last_name").toString());
            result.setUsername(doc.get("username").toString());
            result.setRole(doc.get("role").toString().equals("admin")
                ? UserResponse.RoleEnum.ADMIN
                : UserResponse.RoleEnum.USER);
            results.add(result);
        }
        return new ResponseEntity<List<UserResponse>>(results, HttpStatus.OK);
    }

    public ResponseEntity<TokenInfoResponse> tokenInfo(@Valid String body) {
        JwtUtil jwtUtil = new JwtUtil();
        TokenInfoResponse response = jwtUtil.validateToken(body);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<TokenInfoResponse>(response, HttpStatus.OK);
    }


}
