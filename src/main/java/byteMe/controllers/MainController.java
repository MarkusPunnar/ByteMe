package byteMe.controllers;

import byteMe.model.ByteMeUser;
import byteMe.services.MainRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    private final Jdbi jdbi;

    @Autowired
    public MainController(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @RequestMapping("/")
    public String mainPage() {
        return "index";
    }

    @RequestMapping("/about")
    public String aboutPage(Model model) {
        return jdbi.inTransaction(handle -> {
            int userCount = handle.attach(MainRepository.class).getUserCount();
            model.addAttribute("count", userCount);
            return "about";
        });
    }

    @RequestMapping("/tutorial")
    public String tutorialPage() {
        return "tutorial";
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new ByteMeUser());
        return "register";
    }

    @RequestMapping("/create")
    public String createRoom() {
        return "createroom";
    }

    @RequestMapping("/join")
    public String joinRoom() {
        return "joinroom";
    }

    @RequestMapping("/sitemap")
    public String sitemap() {
        return "sitemap";
    }

    @RequestMapping("/autherror")
    public String authError() {
        return "autherror";
    }
}
