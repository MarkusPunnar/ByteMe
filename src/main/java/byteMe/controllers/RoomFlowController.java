package byteMe.controllers;

import byteMe.model.ByteMeElement;
import byteMe.model.ByteMeGrade;
import byteMe.services.GameInstanceService;
import byteMe.services.RoomFlowRepsitory;
import byteMe.services.RoomStartupRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/room")
public class RoomFlowController {

    @Value("${instanceFileLocation}")
    private String pictureLocation;
    private Jdbi jdbi;
    private GameInstanceService gameInstanceService;

    @Autowired
    public RoomFlowController(Jdbi jdbi, GameInstanceService gameInstanceService) {
        this.jdbi = jdbi;
        this.gameInstanceService = gameInstanceService;
    }

    @ResponseBody
    @RequestMapping("/{instanceID}/delete/{user}")
    public void deleteUserData(@PathVariable("instanceID") String instanceIDAsString, @PathVariable("user") String user) {
        int instanceID = Integer.parseInt(instanceIDAsString);
        jdbi.useTransaction(handle -> {
            RoomStartupRepository startupRepository = handle.attach(RoomStartupRepository.class);
            RoomFlowRepsitory roomFlowRepsitory = handle.attach(RoomFlowRepsitory.class);
            int userID = startupRepository.getUserID(user);
            roomFlowRepsitory.deleteUserData(userID, instanceID);
            roomFlowRepsitory.deleteUserFromRoom(userID, instanceID);
        });
    }

    @ResponseBody
    @RequestMapping("/{instanceID}/deleteElementData/{user}/{elementNumber}")
    public void deleteElementData(@PathVariable("instanceID") String instanceIDAsString,
                                  @PathVariable("elementNumber") String elementNumberAsString,
                                  @PathVariable("user") String user) {
        Integer instanceID = Integer.valueOf(instanceIDAsString);
        Integer elementNumber = Integer.valueOf(elementNumberAsString);
        jdbi.useTransaction(handle -> {
            RoomFlowRepsitory roomFlowRepsitory = handle.attach(RoomFlowRepsitory.class);
            RoomStartupRepository startupRepository = handle.attach(RoomStartupRepository.class);
            List<ByteMeElement> byteMeElements = roomFlowRepsitory.getAllElementsByRoom(instanceID);
            int elementID = byteMeElements.get(elementNumber).getElementID();
            int userID = startupRepository.getUserID(user);
            roomFlowRepsitory.deleteGrade(userID, elementID);
        });
    }

    @ResponseBody
    @RequestMapping("/{instanceID}/getUserData/{user}")
    public List<ByteMeGrade> getUserData(@PathVariable("instanceID") String instanceIDAsString, @PathVariable("user") String user) {
        int instanceID = Integer.parseInt(instanceIDAsString);
        return jdbi.inTransaction(handle -> {
            RoomFlowRepsitory roomFlowRepsitory = handle.attach(RoomFlowRepsitory.class);
            RoomStartupRepository startupRepository = handle.attach(RoomStartupRepository.class);
            int userID = startupRepository.getUserID(user);
            return roomFlowRepsitory.getUserGrades(userID, instanceID);
        });
    }


    @ResponseBody
    @RequestMapping("/{instanceID}/{elementID}/edit/{user}/{grade}")
    public void editUserGrade(@PathVariable("instanceID") String instanceIDAsString, @PathVariable("elementID") String elementIDAsString,
                              @PathVariable("user") String user, @PathVariable("grade") String newGrade) {
        int instanceID = Integer.valueOf(instanceIDAsString);
        int elementID = Integer.valueOf(elementIDAsString);
        int grade = Integer.parseInt(newGrade);
        jdbi.useTransaction(handle -> {
            RoomStartupRepository startupRepository = handle.attach(RoomStartupRepository.class);
            RoomFlowRepsitory flowRepsitory = handle.attach(RoomFlowRepsitory.class);
            int userID = startupRepository.getUserID(user);
            flowRepsitory.editGrade(userID, elementID, instanceID, grade);
        });
    }

    @RequestMapping("/{instanceID}/displayElement/{elementNumber}")
    public String createDisplay(@PathVariable("instanceID") String instanceIDAsString,
                                @PathVariable("elementNumber") String elementNumberAsString, Model model) {
        Integer instanceID = Integer.valueOf(instanceIDAsString);
        model.addAttribute("roomID", instanceID);
        Integer elementNumber = Integer.valueOf(elementNumberAsString);
        model.addAttribute("elementNumber", elementNumber);
        return jdbi.inTransaction(handle -> {
            RoomFlowRepsitory roomFlowRepsitory = handle.attach(RoomFlowRepsitory.class);
            RoomStartupRepository startupRepository = handle.attach(RoomStartupRepository.class);
            List<ByteMeElement> elementList = roomFlowRepsitory.getAllElementsByRoom(instanceID);
            if (elementNumber > elementList.size()) {
                return "redirect:/room/" + instanceIDAsString + "/summary";
            }
            ByteMeElement currentElement = elementList.get(elementNumber - 1);
            if (roomFlowRepsitory.getUserGradeCount(startupRepository
                    .getUserID(gameInstanceService.getCurrentUsername(jdbi)), currentElement.getElementID()) != 0) {
                return "redirect:/room/" + instanceID + "/gradeError/" + (elementNumber + 1);
            }
            if (currentElement.getElementType().toLowerCase().equals("text")) {
                model.addAttribute("elementContent", currentElement.getElementContent());
            }
            model.addAttribute("elementType", currentElement.getElementType());
            model.addAttribute("elementID", currentElement.getElementID());
            return "display";
        });
    }

    @RequestMapping(value = "/{instanceID}/getImage/{elementNumber}", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public FileSystemResource showImage(@PathVariable("instanceID") String instanceIDAsString,
                                        @PathVariable("elementNumber") String elementNumberAsString) {
        Integer elementNumber = Integer.valueOf(elementNumberAsString);
        String pictureFileName = instanceIDAsString + "_" + (elementNumber - 1) + ".png";
        String picturePath = pictureLocation + pictureFileName;
        return new FileSystemResource(picturePath);
    }

    @RequestMapping(value = "/{instanceID}/gradeElement/{elementNumber}", method = RequestMethod.POST)
    public String gradeElement(@PathVariable("instanceID") String instanceIDAsString,
                               @PathVariable("elementNumber") String elementNumberAsString,
                               @ModelAttribute("grade") Integer grade,
                               @ModelAttribute("current") String elementIDAsString) {
        if (grade < 1 || grade > 10) {
            return "redirect:/room/" + instanceIDAsString + "/displayElement/" + elementNumberAsString + "?valueerror";
        }
        int elementNumber = Integer.valueOf(elementNumberAsString);
        int instanceID = Integer.valueOf(instanceIDAsString);
        int elementID = Integer.valueOf(elementIDAsString);
        return jdbi.inTransaction(handle -> {
            RoomFlowRepsitory roomFlowRepsitory = handle.attach(RoomFlowRepsitory.class);
            RoomStartupRepository roomStartupRepository = handle.attach(RoomStartupRepository.class);
            int gradeUser = roomStartupRepository.getUserID(gameInstanceService.getCurrentUsername(jdbi));
            roomFlowRepsitory.saveGrade(instanceID, elementID, grade, gradeUser);
            return "redirect:/room/" + instanceIDAsString + "/displayElement/" + (elementNumber + 1);
        });
    }

    @RequestMapping("/{instanceID}/summary")
    public String showSummary(@PathVariable("instanceID") String instanceIDAsString, Model model) {
        int instanceID = Integer.parseInt(instanceIDAsString);
        return jdbi.inTransaction(handle -> {
            RoomFlowRepsitory roomFlowRepsitory = handle.attach(RoomFlowRepsitory.class);
            RoomStartupRepository roomRepository = handle.attach(RoomStartupRepository.class);
            String hostname = roomRepository.getHostName(instanceID);
            int elementCount = roomFlowRepsitory.getAllElementsByRoom(instanceID).size();
            int secondsFromCreation = roomFlowRepsitory.getSecondsFromCreation(instanceID);
            int secondsToClosure = 3600 - secondsFromCreation;
            String creationTimeString = gameInstanceService.formatTimeFromCreation(secondsFromCreation);
            String closureTimeString = gameInstanceService.formatTimeFromCreation(secondsToClosure);
            model.addAttribute("instanceID", instanceID);
            model.addAttribute("fromCreation", creationTimeString);
            model.addAttribute("toClosure", closureTimeString);
            model.addAttribute("hostname", hostname);
            model.addAttribute("elementCount", elementCount);
            model.addAttribute("userCount", roomFlowRepsitory.getGradedUserCount(instanceID));
            return "summary";
        });
    }

    @RequestMapping("/{instanceID}/gradeError/{elementNumber}")
    public String gradeError(@PathVariable("elementNumber") String elementNumber, Model model,
                             @PathVariable("instanceID") String instanceID) {
        model.addAttribute("elementNumber", elementNumber);
        model.addAttribute("instanceID", instanceID);
        return "gradeError";
    }

    @RequestMapping("/{instanceID}/timestamps")
    @ResponseBody
    public Map<String, String> getTimestamps(@PathVariable("instanceID") String instanceIDAsString) {
        int instanceID = Integer.parseInt(instanceIDAsString);
        return jdbi.inTransaction(handle -> {
            Map<String, String> timestampMap = new HashMap<>();
            RoomFlowRepsitory roomFlowRepsitory = handle.attach(RoomFlowRepsitory.class);
            int secondsFromCreation = roomFlowRepsitory.getSecondsFromCreation(instanceID);
            int secondsToClosure = 3600 - secondsFromCreation;
            String timeFromCreation = gameInstanceService.formatTimeFromCreation(secondsFromCreation);
            String timeToClosure = gameInstanceService.formatTimeFromCreation(secondsToClosure);
            timestampMap.put("creation", timeFromCreation);
            timestampMap.put("closure", timeToClosure);
            return timestampMap;
        });
    }
}
