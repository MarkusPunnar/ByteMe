package byteMe.controllers;


import byteMe.model.RoomUserDataStore;
import byteMe.services.GameInstanceService;
import byteMe.services.InstanceRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/session")
public class InstanceController {

    private final GameInstanceService instanceService;
    private final Jdbi jdbi;

    @Autowired
    public InstanceController(GameInstanceService instanceService, Jdbi jdbi) {
        this.instanceService = instanceService;
        this.jdbi = jdbi;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createRoom(@RequestParam("assessment") List<String> instanceElements, Model model) {
        return jdbi.inTransaction(handle -> {
            InstanceRepository repository = handle.attach(InstanceRepository.class);
            int roomID = instanceService.generateInstanceID();
            while (repository.getRoomIDCount(roomID) != 0) {
                roomID = instanceService.generateInstanceID();
            }
            String username = instanceService.getCurrentUsername();
            int hostID = repository.getUserID(username);
            repository.addRoom(roomID, hostID, instanceElements.size());
            for (String instanceElement : instanceElements) {
                repository.addElement(roomID, instanceElement);
            }
            String hostname = repository.getHostName(roomID);
            model.addAttribute("roomID", roomID);
            model.addAttribute("host", hostname);
            return "hostwait";
        });
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public String joinRoom(@ModelAttribute("id") String instanceIDAsString) {
        return jdbi.inTransaction(handle -> {
            if (!instanceIDAsString.matches("\\d+") || instanceIDAsString.length() != 6) {
                return "redirect:/join?inputerror";
            }
            InstanceRepository repository = handle.attach(InstanceRepository.class);
            int instanceID = Integer.valueOf(instanceIDAsString);
            if (repository.getRoomIDCount(instanceID) == 0) {
                return "redirect:/join?error";
            }
            String username = instanceService.getCurrentUsername();
            int userID = repository.getUserID(username);
            repository.addUserToRoom(instanceID, userID);
            return "redirect:/session/waitingroom/" + instanceIDAsString;
        });
    }

    @RequestMapping("/waitingroom/{instanceID}")
    public String waitingRoom(@PathVariable("instanceID") String instanceIDAsString) {
        return "waitingroom";
    }

    @ResponseBody
    @RequestMapping("/{instanceID}/getUsers")
    public RoomUserDataStore getConnectedUsers(@PathVariable("instanceID") String instanceIDAsString) {
        return jdbi.inTransaction(handle -> {
            Integer instanceID = Integer.valueOf(instanceIDAsString);
            InstanceRepository repository = handle.attach(InstanceRepository.class);
            List<String> connectedUsers = repository.getRoomConnectedUsers(instanceID);
            return new RoomUserDataStore(connectedUsers.size(), connectedUsers);
        });
    }
}
