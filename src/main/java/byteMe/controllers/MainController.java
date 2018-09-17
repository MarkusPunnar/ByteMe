package byteMe.controllers;

import byteMe.model.ByteMeUser;
import byteMe.services.MainRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    private final MainRepository mainRepository;

    @Autowired
    public MainController(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    @RequestMapping("/")
    public String mainPage() {
        return "index";
    }

    @RequestMapping("/about")
    public String aboutPage(Model model) {
        int userCount = mainRepository.getUserCount();
        model.addAttribute("count", userCount);
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
