package byteMe.services;

import byteMe.model.ByteMeUser;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public boolean doPasswordsMatch(ByteMeUser newUser) {
        String password = newUser.getPassword();
        String confirm = newUser.getConfirmedPassword();
        return password.equals(confirm);
    }
}
