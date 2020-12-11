package edu.bbte.projectbluebook.datacatalog.users.util;

import edu.bbte.projectbluebook.datacatalog.users.model.TokenInfoResponse;
import edu.bbte.projectbluebook.datacatalog.users.model.UserResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class UtilCollection {

    private final SecureRandom secureRandom = new SecureRandom();
    private final PasswordEncoder passwordencoder = new BCryptPasswordEncoder(10, secureRandom);
    private final JwtUtil jwtUtil = new JwtUtil();

    public boolean isPasswordGood(String hashed, String password) {
        return passwordencoder.matches(hashed, password);
    }

    public String encodePassword(String password) {
        return passwordencoder.encode(password);
    }

    public String generateJwt(UserResponse userResponse) {
        return jwtUtil.generateToken(userResponse);
    }

    public TokenInfoResponse validateToken(String body) {
        return jwtUtil.validateToken(body);
    }

}
