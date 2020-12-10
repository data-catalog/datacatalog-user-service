package edu.bbte.projectbluebook.datacatalog.users;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import edu.bbte.projectbluebook.datacatalog.users.helpers.Util;
import edu.bbte.projectbluebook.datacatalog.users.model.UserLoginRequest;
import edu.bbte.projectbluebook.datacatalog.users.model.UserLoginResponse;
import edu.bbte.projectbluebook.datacatalog.users.model.UserResponse;
import edu.bbte.projectbluebook.datacatalog.users.repository.UserMongoRepository;
import edu.bbte.projectbluebook.datacatalog.users.service.UserMongoService;
import edu.bbte.projectbluebook.datacatalog.users.util.JwtUtil;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class LoginTest {

    @Autowired
    private UserMongoService service;

    @MockBean
    private UserMongoRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @MockBean
    private Util utils;

    @Test
    public void Login_InvalidUserName() {
        UserLoginRequest loginRequest = createLoginRequest();

        when(repository.isPresent(new Document("username",loginRequest.getUsername())))
                .thenReturn(false);
        assertEquals("User Doesnt Exist"
            ,new ResponseEntity<>(HttpStatus.UNAUTHORIZED),
            service.login(loginRequest));
    }

    @Test
    public void Login_InvalidPassword() {
        UserLoginRequest loginRequest = createLoginRequest();

        FindIterable<Document> iterable = mock(FindIterable.class);
        MongoCursor cursor = mock(MongoCursor.class);

        when(repository.findByFilter(any(Document.class))).thenReturn(iterable);
        when(iterable.iterator()).thenReturn(cursor);
        when(cursor.hasNext()).thenReturn(true).thenReturn(false);
        when(cursor.next()).thenReturn(new Document("password", loginRequest.getPassword()));
        when(iterable.first()).thenReturn(new Document("password", loginRequest.getPassword()));

        when(repository.isPresent(new Document("username",loginRequest.getUsername())))
            .thenReturn(true);
        when(passwordEncoder.matches(any(CharSequence.class), anyString())).thenReturn(false);
        assertEquals("Password doesnt match"
            ,new ResponseEntity<>(HttpStatus.UNAUTHORIZED),
            service.login(loginRequest));
    }
    @Test
    public void Login_Success() {
        UserLoginRequest loginRequest = createLoginRequest();

        UserResponse userResponse = new UserResponse();
        userResponse.setUsername("test");
        userResponse.setEmail("mail@example.com");
        userResponse.setFirstName("Test");
        userResponse.setLastName("User");
        userResponse.setId("507f191e810c19729de860ea");
        userResponse.setRole(UserResponse.RoleEnum.USER);

        UserLoginResponse userLoginResponse = new UserLoginResponse();
        userLoginResponse.setToken("token");
        userLoginResponse.setUser(userResponse);


        Document responseDocument = new Document("_id", new ObjectId("507f191e810c19729de860ea"));
        responseDocument.append("email", "mail@example.com");
        responseDocument.append("password", "password");
        responseDocument.append("username", "test");
        responseDocument.append("first_name", "Test");
        responseDocument.append("last_name", "User");
        responseDocument.append("role", "user");

        FindIterable<Document> iterable = mock(FindIterable.class);
        MongoCursor cursor = mock(MongoCursor.class);

        when(repository.findByFilter(any(Document.class))).thenReturn(iterable);
        when(iterable.iterator()).thenReturn(cursor);
        when(cursor.hasNext()).thenReturn(true).thenReturn(false);
        when(cursor.next()).thenReturn(responseDocument);
        when(iterable.first()).thenReturn(responseDocument);

        when(repository.isPresent(new Document("username",loginRequest.getUsername())))
            .thenReturn(true);

        when(utils.generateJwt(any(UserResponse.class))).thenReturn("token");

        when(utils.isPasswordGood(any(CharSequence.class), anyString())).thenReturn(true);

        assertEquals("Login successful"
            ,new ResponseEntity<>(userLoginResponse ,HttpStatus.OK),
            service.login(loginRequest));
    }


    private UserLoginRequest createLoginRequest() {
        UserLoginRequest toReturn = new UserLoginRequest();
        toReturn.setUsername("test");
        toReturn.setPassword("password");
        return toReturn;
    }
}
