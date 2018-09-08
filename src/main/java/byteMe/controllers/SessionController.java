package byteMe.controllers;


import byteMe.model.Session;
import byteMe.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/session")
public class SessionController {

    private SessionService sessionService;
    private List<Session> sessionCollection = new ArrayList<>();
    private List<Integer> sessionIDCollection = new ArrayList<>();

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/create")
    public String createRoom(@RequestParam("assessment") List<String> elements) {
        for (String element: elements) {
            System.out.println(element);
        }
        int sessionID = sessionService.generateSessionID();
        while (sessionIDCollection.contains(sessionID)) {
            sessionID = sessionService.generateSessionID();
        }
        sessionIDCollection.add(sessionID);
        //Get form inputs and add them to list
        Session newSession = new Session(sessionID, null, new HashMap<>());
        sessionCollection.add(newSession);
        return "sessionroom";
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public String joinRoom() {
        //get sessionID from form
        int sessionID = 2043;
        //check if session with such id exists
        if (!sessionIDCollection.contains(sessionID)) {
            return "joinroom";
        }
        //if not, give error message

        //if exists, check session status and display it

        return null;
    }


}
