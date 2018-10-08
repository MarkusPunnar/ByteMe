package byteMe.services;

import byteMe.model.ByteMeUser;
import byteMe.model.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class AuthService {

    private final JavaMailSender javaMailSender;
    private final PasswordEncoder encoder;

    @Autowired
    public AuthService(JavaMailSender javaMailSender, PasswordEncoder encoder) {
        this.javaMailSender = javaMailSender;
        this.encoder = encoder;
    }

    public boolean doPasswordsMatch(ByteMeUser newUser) {
        String password = newUser.getPassword();
        String confirm = newUser.getConfirmedPassword();
        return password.equals(confirm);
    }

    public void sendEmail(String emailTo) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("noreply@dev.punnar.eu");
        mailMessage.setTo(emailTo);
        mailMessage.setSubject("Byteme registration");
        mailMessage.setText("Thank you for your registration to ByteMe!");
        javaMailSender.send(mailMessage);
    }

    public void addAuthInfoToModel(Model model) {
        model.addAttribute("loggedIn", isAuthenticated());
    }

    public boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated();
    }

    public UserDAO createUserDAO(ByteMeUser user) {
        return new UserDAO(null,user.getUsername(), encoder.encode(user.getPassword()),
                user.getEmail(), "user");
    }
}
