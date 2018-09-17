package byteMe.controllers;


import byteMe.services.GameInstanceService;
import byteMe.services.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/session")
public class InstanceController {

    private final GameInstanceService instanceService;
    private final RoomRepository roomRepository;

    @Autowired
    public InstanceController(GameInstanceService instanceService, RoomRepository roomRepository) {
        this.instanceService = instanceService;
        this.roomRepository = roomRepository;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createRoom(@RequestParam("assessment") List<String> instanceElements, Model model) {
        int roomID = instanceService.generateInstanceID();
        while (roomRepository.getRoomIDCount(roomID)!= 0) {
            roomID = instanceService.generateInstanceID();
        }
        roomRepository.addRoom(roomID, 1, instanceElements.size());
        for (String instanceElement : instanceElements) {
            roomRepository.addElement(roomID, instanceElement);
        }
        String hostname = roomRepository.getHostName(roomID);
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
        return "redirect:/session/waitingroom/" + instanceIDAsString;
    }

    @RequestMapping("/waitingroom/{instanceID}")
    public String waitingRoom(@PathVariable("instanceID") String instanceIDAsString) {
        return "waitingroom";
    }
}
