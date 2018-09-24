package byteMe.controllers;

import byteMe.model.ByteMeUser;
import byteMe.model.UserDAO;
import byteMe.services.AuthRepository;
import byteMe.services.AuthService;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final Jdbi jdbi;

    @Autowired
    public AuthController(AuthService authService, Jdbi jdbi) {
        this.authService = authService;
        this.jdbi = jdbi;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute ByteMeUser newUser, Model model) {
        authService.addAuthInfoToModel(model);
        return jdbi.inTransaction(handle -> {
            AuthRepository authRepository = handle.attach(AuthRepository.class);
            if (!authService.doPasswordsMatch(newUser)) {
                return "redirect:/register?passworderror";
            }
            List<String> registeredUsers = authRepository.getAllUsernames();
            if (registeredUsers.contains(newUser.getUsername())) {
                return "redirect:/register?usernameerror";
            }
            UserDAO userToRegister = authService.createUserDAO(newUser);
            authRepository.registerUser(userToRegister);
//            Thread thread = new Thread(() -> authService.sendEmail(newUser.getEmail()));
//            thread.setDaemon(true);
//            thread.start();
            return "success";
        });
    }
}
