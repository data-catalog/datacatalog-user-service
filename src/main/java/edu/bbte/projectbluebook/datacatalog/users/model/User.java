package edu.bbte.projectbluebook.datacatalog.users.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Document
public class User extends BaseEntity {
    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true)
    private String username;

    private String firstName;

    private String lastName;

    private String password;

    private String role;
}
