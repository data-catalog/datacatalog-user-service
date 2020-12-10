package edu.bbte.projectbluebook.datacatalog.users;

import com.mongodb.MongoException;
import edu.bbte.projectbluebook.datacatalog.users.repository.UserMongoRepository;
import edu.bbte.projectbluebook.datacatalog.users.service.UserMongoService;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class DeleteTest {

    @Autowired
    private UserMongoService service;

    @MockBean
    private UserMongoRepository repository;

    @Test
    public void delete_NotFound_MongoException() {
        String toDeleteId = "507f191e810c19729de860ea";
        Mockito.when(repository.delete(new Document("_id", toDeleteId))).thenThrow(MongoException.class);
        assertEquals("NOT FOUND"
            ,new ResponseEntity<>(HttpStatus.NOT_FOUND)
            ,service.deleteUser(toDeleteId));
    }

    @Test
    public void delete_NotFound_Null() {
        String toDeleteId = "507f191e810c19729de860ea";
        Mockito.when(repository.delete(new Document("_id", toDeleteId))).thenReturn(null);
        assertEquals("NOT FOUND"
            ,new ResponseEntity<>(HttpStatus.NOT_FOUND)
            ,service.deleteUser(toDeleteId));
    }

    @Test
    public void delete_Success() {
        String toDeleteId = "507f191e810c19729de860ea";
        Mockito.when(repository.delete(new Document("_id", toDeleteId)))
                .thenReturn(new Document("_id", "507f191e810c19729de860ea"));
        assertEquals("NO_CONTENT"
            ,new ResponseEntity<>(HttpStatus.NO_CONTENT)
            ,service.deleteUser(toDeleteId));
    }

}
