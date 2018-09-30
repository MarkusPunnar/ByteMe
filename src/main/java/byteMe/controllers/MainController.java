package byteMe.controllers;

import byteMe.model.ByteMeUser;
import byteMe.services.AuthService;
import byteMe.services.MainRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    private final Jdbi jdbi;
    private final AuthService authService;

    @Autowired
    public MainController(Jdbi jdbi, AuthService authService) {
        this.jdbi = jdbi;
        this.authService = authService;
    }

    @RequestMapping("/")
    public String mainPage(Model model) {
        authService.addAuthInfoToModel(model);
        return "index";
    }

    @RequestMapping("/about")
    public String aboutPage(Model model) {
        authService.addAuthInfoToModel(model);
        return jdbi.inTransaction(handle -> {
            int userCount = handle.attach(MainRepository.class).getUserCount();
            model.addAttribute("count", userCount);
            return "about";
        });
    }

    @RequestMapping("/tutorial")
    public String tutorialPage(Model model) {
        authService.addAuthInfoToModel(model);
        return "tutorial";
    }

    @RequestMapping("/login")
    public String loginPage(Model model) {
        authService.addAuthInfoToModel(model);
        return "login";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        authService.addAuthInfoToModel(model);
        model.addAttribute("user", new ByteMeUser());
        return "register";
    }

    @RequestMapping("/create")
    public String createRoom(Model model) {
        authService.addAuthInfoToModel(model);
        return "createroom";
    }

    @RequestMapping("/join")
    public String joinRoom(Model model) {
        authService.addAuthInfoToModel(model);
        return "joinroom";
    }

    @RequestMapping("/sitemap")
    public String sitemap(Model model) {
        authService.addAuthInfoToModel(model);
        return "sitemap";
    }
}
