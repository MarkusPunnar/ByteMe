package byteMe.controllers;


import byteMe.services.GameInstanceService;
import byteMe.services.InstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/session")
public class InstanceController {

    private final GameInstanceService instanceService;
    private final InstanceRepository instanceRepository;

    @Autowired
    public InstanceController(GameInstanceService instanceService, InstanceRepository instanceRepository) {
        this.instanceService = instanceService;
        this.instanceRepository = instanceRepository;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createRoom(@RequestParam("assessment") List<String> instanceElements, Model model) {
        int roomID = instanceService.generateInstanceID();
        while (instanceRepository.getRoomIDCount(roomID)!= 0) {
            roomID = instanceService.generateInstanceID();
        }
        String username = instanceService.getCurrentUsername();
        int hostID = instanceRepository.getUserID(username);
        instanceRepository.addRoom(roomID, hostID, instanceElements.size());
        for (String instanceElement : instanceElements) {
            instanceRepository.addElement(roomID, instanceElement);
        }
        String hostname = instanceRepository.getHostName(roomID);
        model.addAttribute("roomID", roomID);
        model.addAttribute("host", hostname);
        return "hostwait";
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public String joinRoom(@ModelAttribute("id") String instanceIDAsString) {
        if (!instanceIDAsString.matches("\\d+") || instanceIDAsString.length() != 6) {
            return "redirect:/join?inputerror";
        }
        int instanceID = Integer.valueOf(instanceIDAsString);
        if (instanceRepository.getRoomIDCount(instanceID) == 0) {
            return "redirect:/join?error";
        }
        String username = instanceService.getCurrentUsername();
        int userID = instanceRepository.getUserID(username);
        instanceRepository.addUserToRoom(instanceID, userID);
        return "redirect:/session/waitingroom/" + instanceIDAsString;
    }

    @RequestMapping("/waitingroom/{instanceID}")
    public String waitingRoom(@PathVariable("instanceID") String instanceIDAsString) {
        int instanceID = Integer.valueOf(instanceIDAsString);

        return "waitingroom";
    }

    @ResponseBody
    @RequestMapping("/{instanceID}/getUsers")
    public String getConnectedUsers(@PathVariable("instanceID") String instanceIDAsString) {
        System.out.println("Request made");
        return "Request made";
    }
}
