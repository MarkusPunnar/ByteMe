package byteMe.controllers;

import byteMe.model.AssessmentElement;
import byteMe.model.ByteMeUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String mainPage() {
        return "index";
    }

    @RequestMapping("/about")
    public String aboutPage() {
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
