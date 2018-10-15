package byteMe.controllers;


import byteMe.model.RoomUserDataStore;
import byteMe.model.UserDAO;
import byteMe.services.AuthRepository;
import byteMe.services.AuthService;
import byteMe.services.GameInstanceService;
import byteMe.services.RoomStartupRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/session")
public class RoomStartupController {

    @Value("${instanceFileLocation}")
    private String instanceFilePath;
    private final GameInstanceService instanceService;
    private final Jdbi jdbi;
    private final AuthService authService;

    @Autowired
    public RoomStartupController(GameInstanceService instanceService, Jdbi jdbi, AuthService authService) {
        this.instanceService = instanceService;
        this.jdbi = jdbi;
        this.authService = authService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createRoom(@RequestParam("assessment") List<String> instanceElements,
            @RequestParam("picture") List<MultipartFile> instancePictures, Model model) throws IOException {
        authService.addAuthInfoToModel(model);
        return jdbi.inTransaction(handle -> {
            RoomStartupRepository repository = handle.attach(RoomStartupRepository.class);
            int roomID = instanceService.generateInstanceID();
            while (repository.getRoomIDCount(roomID) != 0) {
                roomID = instanceService.generateInstanceID();
            }
            String displayname = instanceService.getCurrentUsername(jdbi);
            int hostID = repository.getUserID(displayname);
            repository.addRoom(roomID, hostID, instanceElements.size());
            for (String instanceElement : instanceElements) {
                repository.addElement(roomID, instanceElement, "text");
            }
            for (int i = 0; i < instancePictures.size(); i++) {
                String picturePath = instanceFilePath + roomID  + "_" + (i+1) + ".png";
                instancePictures.get(i).transferTo(new File(picturePath));
                repository.addElement(roomID, picturePath, "picture");
            }
            String hostname = repository.getHostName(roomID);
            model.addAttribute("roomID", roomID);
            model.addAttribute("host", hostname);
            instanceService.getRoomStatusStore().put(roomID, false);
            return "hostwait";
        });
    }

    @RequestMapping(value = "/create/fileupload", method = RequestMethod.POST)
    public void uploadFile(){

    }
    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public String joinRoom(@ModelAttribute("id") String instanceIDAsString, Model model) {
        authService.addAuthInfoToModel(model);
        return jdbi.inTransaction(handle -> {
            if (!instanceIDAsString.matches("\\d+") || instanceIDAsString.length() != 6) {
                return "redirect:/join?inputerror";
            }
            RoomStartupRepository repository = handle.attach(RoomStartupRepository.class);
            int instanceID = Integer.valueOf(instanceIDAsString);
            if (repository.getRoomIDCount(instanceID) == 0) {
                return "redirect:/join?error";
            }
            String username = instanceService.getCurrentUsername(jdbi);
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
            RoomStartupRepository repository = handle.attach(RoomStartupRepository.class);
            List<String> connectedUsers = repository.getRoomConnectedUsers(instanceID);
            return new RoomUserDataStore(connectedUsers.size(), connectedUsers);
        });
    }

    @PostMapping(value = "/{instanceID}/room")
    public String startRoom(@PathVariable("instanceID") String instanceID) {
        instanceService.getRoomStatusStore().put(Integer.valueOf(instanceID), true);
        return "adminview";
    }

    @RequestMapping("/{instanceID}/room")
    public String enterAdminView(@PathVariable("instanceID") String instanceIDAsString) {
        int instanceID = Integer.parseInt(instanceIDAsString);
        return jdbi.inTransaction(handle -> {
            AuthRepository authRepository = handle.attach(AuthRepository.class);
            String loggedInUser = instanceService.getCurrentUsername(jdbi);
            if (loggedInUser == null) {
                return "redirect:/autherror";
            }
            UserDAO user = authRepository.getUserData(loggedInUser);
            int userID = user.getUserID();
            if (userID != authRepository.getHostIdByRoom(instanceID)) {
                return "redirect:/autherror";
            }
            return "adminview";
        });
    }

    @ResponseBody
    @RequestMapping("/{instanceID}/status")
    public String getRoomStatus(@PathVariable("instanceID") String instanceID) {
        if (instanceService.getRoomStatusStore().get(Integer.valueOf(instanceID))) {
            return "true";
        }
        return "false";
    }
}
