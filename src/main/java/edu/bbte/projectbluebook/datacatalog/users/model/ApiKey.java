package edu.bbte.projectbluebook.datacatalog.users.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@Data
@Accessors(chain = true)
@Document
public class ApiKey {
    private String id = new ObjectId().toHexString();

    private String title;

    private String key;

    private OffsetDateTime createdAt;
}
