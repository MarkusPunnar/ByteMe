package byteMe.controllers;

import byteMe.model.ByteMeUser;
import byteMe.model.UserEntity;
import byteMe.services.AuthService;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String registerUser(@ModelAttribute ByteMeUser newUser) {
        if (!authService.doPasswordsMatch(newUser)) {
            return "redirect:/register?passworderror";
        }
        UserEntity user = new UserEntity(newUser.getUsername(), newUser.getPassword(), newUser.getEmail(), "user");
        jdbi.useTransaction(handle -> {
            int id = handle.createUpdate("INSERT INTO users (username, hashedPassword, useremail, userrole)" +
                    " VALUES (:username, :hashedPassword, :email, :role)").bindBean(user)
                    .executeAndReturnGeneratedKeys("userid").mapTo(int.class).findOnly();
            user.setId(id);
        });
        return "success";
    }
}
