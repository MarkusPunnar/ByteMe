package byteMe.controllers;


import byteMe.model.AssessmentElement;
import byteMe.model.Session;
import byteMe.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.awt.*;
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

    @RequestMapping(value = "/create", method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createRoom(@RequestBody List<String> elementData) {
        System.out.println(elementData.get(0));
        int sessionID = sessionService.generateSessionID();
        while (sessionIDCollection.contains(sessionID)) {
            sessionID = sessionService.generateSessionID();
        }
        sessionIDCollection.add(sessionID);
        List<AssessmentElement> assessmentElements = new ArrayList<>();
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
