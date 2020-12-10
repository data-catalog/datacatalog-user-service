package edu.bbte.projectbluebook.datacatalog.users;

import edu.bbte.projectbluebook.datacatalog.users.helpers.Util;
import edu.bbte.projectbluebook.datacatalog.users.model.TokenInfoResponse;
import edu.bbte.projectbluebook.datacatalog.users.service.UserMongoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class TokenInfoTest {

    @Autowired
    private UserMongoService service;

    @MockBean
    private Util utils;

    @Test
    public void TokenInfo_BadRequest() {
        String body = "null";

        when(utils.validateToken(body)).thenReturn(null);
        assertEquals("Invalid body"
            ,new ResponseEntity<>(HttpStatus.BAD_REQUEST)
            ,service.tokenInfo(body));
    }

    @Test
    public void TokenInfo_Success() {
        String body = "ValidBody";
        TokenInfoResponse response = new TokenInfoResponse();
        response.setActive(true);
        response.setUsername("test");
        response.setUserId("adad");
        response.setRole(TokenInfoResponse.RoleEnum.USER);
        response.setIat(10);
        response.setExp(11);

        when(utils.validateToken(body)).thenReturn(response);
        assertEquals("TokenInfo Sent"
            ,new ResponseEntity<>(response, HttpStatus.OK)
            ,service.tokenInfo(body));
    }

}
