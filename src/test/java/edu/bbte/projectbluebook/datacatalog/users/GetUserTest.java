package edu.bbte.projectbluebook.datacatalog.users;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import edu.bbte.projectbluebook.datacatalog.users.model.UserResponse;
import edu.bbte.projectbluebook.datacatalog.users.repository.UserMongoRepository;
import edu.bbte.projectbluebook.datacatalog.users.service.UserMongoService;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class GetUserTest {
    @Autowired
    private UserMongoService service;

    @MockBean
    private UserMongoRepository repository;

    @Test
    public void testGetAsset_NotFound_ThrowsException() {
        String userId = "507f191e810c19729de860ea";
        Document user = new Document("_id", new ObjectId(userId));
        user.append("email", "mail@example.com");
        user.append("first_name", "Test");
        user.append("last_append", "User");
        user.append("username", "test");
        user.append("role", "user");

        FindIterable<Document> iterable = mock(FindIterable.class);
        MongoCursor cursor = mock(MongoCursor.class);

        when(repository.findByFilter(any(Document.class))).thenReturn(iterable);
        when(iterable.iterator()).thenReturn(cursor);
        when(cursor.hasNext()).thenReturn(true).thenReturn(false);
        when(iterable.first()).thenThrow(IllegalArgumentException.class);

        assertEquals("User Not Found",
                new ResponseEntity<>(HttpStatus.NOT_FOUND),
                service.getUser(userId));
    }

    @Test
    public void testGetAsset_NotFound_NullDocument() {
        String userId = "507f191e810c19729de860ea";
        Document user = new Document("_id", new ObjectId(userId));
        user.append("email", "mail@example.com");
        user.append("first_name", "Test");
        user.append("last_append", "User");
        user.append("username", "test");
        user.append("role", "user");

        FindIterable<Document> iterable = mock(FindIterable.class);
        MongoCursor cursor = mock(MongoCursor.class);

        when(repository.findByFilter(any(Document.class))).thenReturn(iterable);
        when(iterable.iterator()).thenReturn(cursor);
        when(cursor.hasNext()).thenReturn(true).thenReturn(false);
        when(iterable.first()).thenReturn(null);

        assertEquals("User Not Found",
                new ResponseEntity<>(HttpStatus.NOT_FOUND),
                service.getUser(userId));
    }

    @Test
    public void testGetAsset_NotFound_Success() {
        String userId = "507f191e810c19729de860ea";
        Document user = new Document("_id", new ObjectId(userId));
        user.append("email", "mail@example.com");
        user.append("first_name", "Test");
        user.append("last_name", "User");
        user.append("username", "test");
        user.append("role", "user");

        UserResponse result = new UserResponse();
        result.setRole(UserResponse.RoleEnum.USER);
        result.setId(userId);
        result.setEmail("mail@example.com");
        result.setFirstName("Test");
        result.setLastName("User");
        result.setUsername("test");

        FindIterable<Document> iterable = mock(FindIterable.class);
        MongoCursor cursor = mock(MongoCursor.class);

        when(repository.findByFilter(any(Document.class))).thenReturn(iterable);
        when(iterable.iterator()).thenReturn(cursor);
        when(cursor.hasNext()).thenReturn(true).thenReturn(false);
        when(iterable.first()).thenReturn(user);

        assertEquals("User Found",
                new ResponseEntity<>(result, HttpStatus.OK),
                service.getUser(userId));
    }
}
