package byteMe.controllers;

import byteMe.model.ByteMeUser;
import byteMe.services.AuthRepository;
import byteMe.services.AuthService;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder encoder;
    private final Jdbi jdbi;

    @Autowired
    public AuthController(AuthService authService, PasswordEncoder encoder, Jdbi jdbi) {
        this.authService = authService;
        this.encoder = encoder;
        this.jdbi = jdbi;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute ByteMeUser newUser) {
        return jdbi.inTransaction(handle -> {
            AuthRepository authRepository = handle.attach(AuthRepository.class);
            if (!authService.doPasswordsMatch(newUser)) {
                return "redirect:/register?passworderror";
            }
            List<String> registeredUsers = authRepository.getAllUsernames();
            if (registeredUsers.contains(newUser.getUsername())) {
                return "redirect:/register?usernameerror";
            }
            authRepository.registerUser(newUser.getUsername(), encoder.encode(newUser.getPassword()), newUser.getEmail(), "user");
//            Thread thread = new Thread(() -> authService.sendEmail(newUser.getEmail()));
//            thread.setDaemon(true);
//            thread.start();
            return "success";
        });
    }
}
