package edu.bbte.projectbluebook.datacatalog.users.repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.stereotype.Repository;

@Repository
public class UserMongoRepository {

    private final MongoClientURI uri = new MongoClientURI("mongodb+srv:"
            + "//m001-student:m001-mongodb-basics@cluster0.dlhll.mongodb.net/DataCatalog?retryWrites=true&w=majority");
    private final MongoClient mongoClient = new MongoClient(uri);
    private final MongoDatabase database = mongoClient.getDatabase("DataCatalog");
    private final MongoCollection<Document> users = database.getCollection("Users");

    public FindIterable<Document> findByFilter(Document filter) {
        return users.find(filter);
    }

    public FindIterable<Document> findAll() {
        return users.find();
    }

    public boolean insert(Document user) {
        try {
            users.insertOne(user);
        } catch (MongoException e) {
            return false;
        }
        return true;
    }

    public Document delete(Document id) {
        return users.findOneAndDelete(id);
    }

    public boolean isPresent(Document filter) {
        return findByFilter(filter).first() != null;
    }
}
