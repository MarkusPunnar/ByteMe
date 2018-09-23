package byteMe.controllers;


import byteMe.model.RoomUserDataStore;
import byteMe.services.AuthService;
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
    private final AuthService authService;

    @Autowired
    public InstanceController(GameInstanceService instanceService, Jdbi jdbi, AuthService authService) {
        this.instanceService = instanceService;
        this.jdbi = jdbi;
        this.authService = authService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createRoom(@RequestParam("assessment") List<String> instanceElements, Model model) {
        authService.addAuthInfoToModel(model);
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
            instanceService.getRoomStatusStore().put(roomID, false);
            return "hostwait";
        });
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public String joinRoom(@ModelAttribute("id") String instanceIDAsString, Model model) {
        authService.addAuthInfoToModel(model);
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
    public String waitingRoom(@PathVariable("instanceID") String instanceID, Model model) {
        authService.addAuthInfoToModel(model);
        model.addAttribute("instanceID", instanceID);
        return "waitingroom";
    }

    @ResponseBody
    @RequestMapping("/{instanceID}/getUsers")
    public RoomUserDataStore getConnectedUsers(@PathVariable("instanceID") String instanceIDAsString, Model model) {
        authService.addAuthInfoToModel(model);
        return jdbi.inTransaction(handle -> {
            Integer instanceID = Integer.valueOf(instanceIDAsString);
            InstanceRepository repository = handle.attach(InstanceRepository.class);
            List<String> connectedUsers = repository.getRoomConnectedUsers(instanceID);
            return new RoomUserDataStore(connectedUsers.size(), connectedUsers);
        });
    }

    @RequestMapping(value = "/{instanceID}/room", method = RequestMethod.POST)
    public String startRoom(@PathVariable("instanceID") String instanceID, Model model) {
        authService.addAuthInfoToModel(model);
        instanceService.getRoomStatusStore().put(Integer.valueOf(instanceID), true);
        return "displayhost";
    }

    @ResponseBody
    @RequestMapping("/{instanceID}/status")
    public String getRoomStatus(@PathVariable("instanceID") String instanceID, Model model) {
        authService.addAuthInfoToModel(model);
        if (instanceService.getRoomStatusStore().get(Integer.valueOf(instanceID))) {
            return "true";
        }
        return "false";
    }

    @RequestMapping("/{instanceID}/enterRoom")
    public String enterRoom(@PathVariable("instanceID") String instanceID) {
        return "display";
    }
}
