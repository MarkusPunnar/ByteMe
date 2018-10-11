package byteMe.controllers;

import byteMe.model.ByteMeUser;
import byteMe.model.UserDAO;
import byteMe.services.AuthRepository;
import byteMe.services.AuthService;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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
    public String registerUser(@ModelAttribute ByteMeUser newUser) {
        return jdbi.inTransaction(handle -> {
            AuthRepository authRepository = handle.attach(AuthRepository.class);
            if (!authService.doPasswordsMatch(newUser)) {
                return "redirect:/register?passworderror";
            }
            List<String> registeredUsers = authRepository.getAllUsernames();
            if (registeredUsers.contains(newUser.getDisplayname())) {
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

    @PostMapping("/login")
    public String formLogin(@ModelAttribute("displayname") String displayname,
                            @ModelAttribute("password") String insertedPassword, HttpServletRequest request) {
        try {
            request.login(displayname, insertedPassword);
        } catch (ServletException ex) {
            return "redirect:/loginform?error";
        }
        return "redirect:/";
    }

    @RequestMapping("/redirect")
    public String authProcess(Authentication authentication, RedirectAttributes redirectAttributes) {
        return jdbi.inTransaction(handle -> {
            AuthRepository authRepository = handle.attach(AuthRepository.class);
            String googleID = ((DefaultOidcUser) authentication.getPrincipal()).getName();
            if (authRepository.getGoogleIDCount(googleID) != 0) {
                return "redirect:/";
            }
            OAuth2User currentUser = ((OAuth2User) authentication.getPrincipal());
            String email = currentUser.getAttributes().get("email").toString();
            authRepository.insertNewGoogleUser(googleID, email);
            redirectAttributes.addAttribute("googleID", googleID);
            return "redirect:/auth/setdisplay";
        });
    }

    @RequestMapping("/setdisplay")
    public String displayForm(@ModelAttribute("googleID") String googleID, Model model) {
        model.addAttribute("googleID", googleID);
        return "setdisplay";
    }

    @PostMapping("/setdisplay")
    public String display(@RequestParam("googleID") String googleID, @RequestParam("displayname") String name) {
        return jdbi.inTransaction(handle -> {
            AuthRepository authRepository = handle.attach(AuthRepository.class);
            authRepository.setNewGoogleUserDisplayname(googleID, name);
            return "redirect:/";
        });
    }
}
