package edu.bbte.projectbluebook.datacatalog.users;

import edu.bbte.projectbluebook.datacatalog.users.model.UserRequest;
import edu.bbte.projectbluebook.datacatalog.users.repository.UserMongoRepository;
import edu.bbte.projectbluebook.datacatalog.users.service.UserMongoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class CreateUserTest {

    @Autowired
    private UserMongoService service;

    @MockBean
    private UserMongoRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void CreateUser_Success() {
        UserRequest request = createRequest();
        Document email = new Document("email", request.getEmail());
        Document user = new Document("username", request.getUsername());
        Document userToInsert = new Document();
        when(repository.isPresent(email)).thenReturn(false);
        when(repository.isPresent(user)).thenReturn(false);
        when(passwordEncoder.encode("testpass")).thenReturn("hashedPass");

        userToInsert.append("username", request.getUsername());
        userToInsert.append("password", "hashedPass");
        userToInsert.append("firstName", request.getFirstName());
        userToInsert.append("lastName", request.getLastName());
        userToInsert.append("email", request.getEmail());
        userToInsert.append("role", request.getRole());

        when(repository.insert(any(Document.class))).thenReturn(true);
        assertEquals("User Succesfully Createad"
            ,new ResponseEntity<>(HttpStatus.CREATED),
            service.createUser(request));

    }

    @Test
    public void CreateUser_EmailAlreadyTaken() {
        UserRequest request = createRequest();
        Document email = new Document("email", request.getEmail());
        when(repository.isPresent(email)).thenReturn(true);
        assertEquals("Email taken"
            ,new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY)
            ,service.createUser(request));
    }

    @Test
    public void CreateUser_UserNameAlreadyTaken() {
        UserRequest request = createRequest();
        Document userName = new Document("username", request.getUsername());
        Document email = new Document("email", request.getEmail());
        when(repository.isPresent(email)).thenReturn(false);
        when(repository.isPresent(userName)).thenReturn(true);
        assertEquals("Username taken"
            ,new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY)
            ,service.createUser(request));
    }

    @Test
    public void CreateUser_RepositoryError() {
        UserRequest request = createRequest();
        Document userName = new Document("username", request.getEmail());
        Document email = new Document("email", request.getEmail());
        when(repository.isPresent(email)).thenReturn(false);
        when(repository.isPresent(userName)).thenReturn(false);
        when(repository.insert(any(Document.class))).thenReturn(false);
        assertEquals("Repository error"
            , new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY)
            , service.createUser(request));
    }

    private UserRequest createRequest() {
        UserRequest toReturn = new UserRequest();
        toReturn.setEmail("testemail@email.com");
        toReturn.setFirstName("Test");
        toReturn.setLastName("User");
        toReturn.setPassword("testpass");
        toReturn.setUsername("testuser");
        toReturn.setRole(UserRequest.RoleEnum.USER);
        return toReturn;
    }
}
