package edu.bbte.projectbluebook.datacatalog.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserServiceException extends RuntimeException {
    public UserServiceException(String message) {
        super(message);
    }
}
