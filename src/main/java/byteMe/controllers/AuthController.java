package byteMe.controllers;

import byteMe.model.ByteMeUser;
import byteMe.model.UserDto;
import byteMe.services.AuthService;
import org.jdbi.v3.core.Jdbi;
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
    private final Jdbi jdbi;
    private final PasswordEncoder encoder;

    public AuthController(AuthService authService, Jdbi jdbi, PasswordEncoder encoder) {
        this.authService = authService;
        this.jdbi = jdbi;
        this.encoder = encoder;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute ByteMeUser newUser) {
        return jdbi.inTransaction(handle -> {
            if (!authService.doPasswordsMatch(newUser)) {
                return "redirect:/register?passworderror";
            }
            UserDto user = new UserDto(newUser.getUsername(),
                    encoder.encode(newUser.getPassword()), newUser.getEmail(), "user");
            List<String> registeredUsers = handle.createQuery("SELECT username FROM Users")
                    .mapTo(String.class).list();
            if (registeredUsers.contains(newUser.getUsername())) {
                return "redirect:/register?usernameerror";
            }
            int id = handle.createUpdate("INSERT INTO Users (username, hashedPassword, useremail, userrole)" +
                    " VALUES (:username, :hashedPassword, :email, :role)").bindBean(user)
                    .executeAndReturnGeneratedKeys("userid").mapTo(int.class).findOnly();
            user.setId(id);
            return "success";
        });
    }
}
