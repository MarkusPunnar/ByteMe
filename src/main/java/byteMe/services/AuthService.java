package byteMe.services;

import byteMe.model.ByteMeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class AuthService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public AuthService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
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
        mailMessage.setText("Test email");
        javaMailSender.send(mailMessage);
    }

    public void addAuthInfoToModel(Model model) {
        model.addAttribute("loggedIn", isAuthenticated());
    }

    public boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated();
    }
}
