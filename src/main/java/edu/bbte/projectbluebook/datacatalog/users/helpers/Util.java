package edu.bbte.projectbluebook.datacatalog.users.helpers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class Util {

    private final SecureRandom secureRandom = new SecureRandom();
    private final PasswordEncoder passwordencoder = new BCryptPasswordEncoder(10, secureRandom);

    public Util() {
    }

    public boolean isPasswordGood(CharSequence charSequence, String password) {
        return passwordencoder.matches(charSequence, password);
    }

    public String encodePassword(String password) {
        return passwordencoder.encode(password);
    }

}
