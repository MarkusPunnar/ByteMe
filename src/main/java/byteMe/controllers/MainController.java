package byteMe.controllers;

import byteMe.model.ByteMeUser;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    private final Jdbi jdbi;

    public MainController(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @RequestMapping("/")
    public String mainPage() {
        return "index";
    }

    @RequestMapping("/about")
    public String aboutPage(Model model) {
        jdbi.useHandle(handle -> {
            int userCount = handle.createQuery("SELECT COUNT(*) FROM Users").mapTo(int.class).findOnly();
            model.addAttribute("count", userCount);
        });
        return "about";
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
}
