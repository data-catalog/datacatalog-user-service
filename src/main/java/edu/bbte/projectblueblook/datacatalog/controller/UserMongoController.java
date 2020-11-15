package edu.bbte.projectblueblook.datacatalog.controller;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import edu.bbte.projectblueblook.datacatalog.api.UserApi;
import edu.bbte.projectblueblook.datacatalog.model.UserLoginRequest;
import edu.bbte.projectblueblook.datacatalog.model.UserLoginResponse;
import edu.bbte.projectblueblook.datacatalog.model.UserRequest;
import edu.bbte.projectblueblook.datacatalog.model.UserResponse;
import edu.bbte.projectblueblook.datacatalog.util.JwtUtil;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserMongoController implements UserApi {

    private final MongoClientURI uri = new MongoClientURI("mongodb+srv://m001-student:m001-mongodb-basics@cluster0.dlhll.mongodb.net/DataCatalog?retryWrites=true&w=majority");
    private final MongoClient mongoClient = new MongoClient(uri);
    private final MongoDatabase database = mongoClient.getDatabase("DataCatalog");
    private final MongoCollection<Document> users = database.getCollection("Users");
    private final SecureRandom secureRandom = new SecureRandom();
    private final PasswordEncoder passwordencoder = new BCryptPasswordEncoder(10, secureRandom);
    private final JwtUtil jwtUtil = new JwtUtil();

    @Override
    public ResponseEntity<Void> createUser(@Valid UserRequest userRequest) {

        String email = userRequest.getEmail();
        // Checking if email is unique
        Document userEmail = users
                .find(new Document("email", email))
                .first();
        if (userEmail != null) {
            return new ResponseEntity<Void>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        // Checking if username is unique
        String username = userRequest.getUsername();
        Document userName = users
                .find(new Document("username", username))
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
        try {
            users.insertOne(user);
        } catch(Exception e) {
            // MongoDB specific error
            //return "Database problem, failed to register.";
            System.out.println(e.getMessage());
            return new ResponseEntity<Void>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UserLoginResponse> login(@Valid UserLoginRequest userLoginRequest) {
        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();
        Document user = users
                .find(new Document("username", username))
                .first();
        if (user == null) {
            return new ResponseEntity<UserLoginResponse>(HttpStatus.UNAUTHORIZED);
        }
        String hashed = user.get("password").toString();
        if (passwordencoder.matches(password, hashed)) {
            UserLoginResponse response = new UserLoginResponse();
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
            response.setUser(userResponse);

            // SET JWT TOKEN HERE
            String token = jwtUtil.generateToken(userResponse);
            response.setToken(token);
            return new ResponseEntity<UserLoginResponse>(response, HttpStatus.OK);
        }
        return new ResponseEntity<UserLoginResponse>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<Void> deleteUser(String userId) {
        try {
            users.findOneAndDelete(new Document("_id", new ObjectId(userId)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<UserResponse> getUser(String userId) {
        Document user = users
                .find(new Document("_id", new ObjectId(userId)))
                .first();
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

    @Override
    public ResponseEntity<List<UserResponse>> getUsers() {
        FindIterable<Document> docs = users.find();
        List<UserResponse> results = new ArrayList<>();
        for(Document doc : docs) {
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
}
