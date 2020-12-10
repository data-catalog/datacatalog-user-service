package edu.bbte.projectbluebook.datacatalog.users;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import edu.bbte.projectbluebook.datacatalog.users.model.UserResponse;
import edu.bbte.projectbluebook.datacatalog.users.repository.UserMongoRepository;
import edu.bbte.projectbluebook.datacatalog.users.service.UserMongoService;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;


@SpringBootTest
public class GetUsersTest {

    @Autowired
    private UserMongoService service;

    @MockBean
    private UserMongoRepository repository;

    @Test
    public void GetUsers_EmptyArray() {

        FindIterable<Document> iterable = mock(FindIterable.class);
        MongoCursor cursor = mock(MongoCursor.class);

        when(repository.findAll()).thenReturn(iterable);
        when(iterable.iterator()).thenReturn(cursor);
        when(cursor.hasNext()).thenReturn(false);

        assertEquals("Empty array.",
            new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK),
            service.getUsers());
    }

    @Test
    public void GetUsers_Success() {
        List<UserResponse> result = new ArrayList<>();
        FindIterable<Document> iterable = mock(FindIterable.class);
        MongoCursor cursor = mock(MongoCursor.class);

        Document user1 = new Document("_id","507f191e810c19729de860ea");
        user1.append("email", "mail@example.com");
        user1.append("first_name", "Test");
        user1.append("last_name", "User");
        user1.append("username", "test");
        user1.append("role", "user");

        Document user2 = new Document("_id","507f191e810c19729de861ea");
        user2.append("email", "mail@exampl2e.com");
        user2.append("first_name", "Tes2t");
        user2.append("last_name", "Use2r");
        user2.append("username", "tes2t");
        user2.append("role", "admin");

        UserResponse response1 = new UserResponse();
        response1.setUsername("test");
        response1.setEmail("mail@example.com");
        response1.setLastName("User");
        response1.setFirstName("Test");
        response1.setRole(UserResponse.RoleEnum.USER);
        response1.setId("507f191e810c19729de860ea");

        UserResponse response2 = new UserResponse();
        response2.setUsername("tes2t");
        response2.setEmail("mail@exampl2e.com");
        response2.setLastName("Use2r");
        response2.setFirstName("Tes2t");
        response2.setRole(UserResponse.RoleEnum.ADMIN);
        response2.setId("507f191e810c19729de861ea");

        when(repository.findAll()).thenReturn(iterable);
        when(iterable.iterator()).thenReturn(cursor);
        when(cursor.hasNext())
            .thenReturn(true)
            .thenReturn(true)
            .thenReturn(false);
        when(cursor.next())
            .thenReturn(user1)
            .thenReturn(user2);

        result.add(response1);
        result.add(response2);

        assertEquals("Array with data.",
            new ResponseEntity<>(result, HttpStatus.OK),
            service.getUsers());
    }

}
