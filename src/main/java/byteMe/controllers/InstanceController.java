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

    private GameInstanceService instanceService;
    private RoomRepository roomRepository;

    @Autowired
    public InstanceController(GameInstanceService instanceService, RoomRepository roomRepository) {
        this.instanceService = instanceService;
        this.roomRepository = roomRepository;
    }

    @PostMapping("/create")
    public String createRoom(@RequestParam("assessment") List<String> instanceElements, Model model) {
        int roomID = instanceService.generateInstanceID();
        while (roomRepository.getRoomIDCount(roomID)!= 0) {
            roomID = instanceService.generateInstanceID();
        }
        roomRepository.addRoom(roomID, 0, instanceElements.size());
        for (String instanceElement : instanceElements) {
            roomRepository.addElement(roomID, instanceElement);
        }
        model.addAttribute("roomID", roomID);
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
