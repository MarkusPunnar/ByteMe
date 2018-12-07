package byteMe.controllers;

import byteMe.model.ByteMeElement;
import byteMe.services.RoomFlowRepsitory;
import byteMe.services.SummaryRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/summary")
public class SummaryController {

    private Jdbi jdbi;

    @Autowired
    public SummaryController(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @RequestMapping("/summarydetails/{instanceID}")
    public String summaryDetails(@PathVariable("instanceID") String instanceID, Model model) {
        model.addAttribute("instanceID", instanceID);
        return "summarydetails";
    }

    @ResponseBody
    @RequestMapping("/users/{instanceID}")
    public List<String> getUsernamesByRoom(@PathVariable("instanceID") String instanceIDAsString) {
        int instanceID = Integer.valueOf(instanceIDAsString);
        return jdbi.inTransaction(handle -> {
            SummaryRepository summaryRepository = handle.attach(SummaryRepository.class);
            return summaryRepository.getRoomUsernames(instanceID);
        });
    }

    @ResponseBody
    @RequestMapping("/elements/{instanceID}")
    public List<ByteMeElement> getElementsByRoom(@PathVariable("instanceID") String instanceIDAsString) {
        int instanceID = Integer.valueOf(instanceIDAsString);
        return jdbi.inTransaction(handle -> {
            RoomFlowRepsitory roomFlowRepsitory = handle.attach(RoomFlowRepsitory.class);
            return roomFlowRepsitory.getAllElementsByRoom(instanceID);
        });
    }

    @ResponseBody
    @RequestMapping("/grades/{instanceID}")
    public List<Integer> getGradesByRoom(String userIDAsString, @PathVariable("instanceID") String instanceIDAsString) {
        int instanceID = Integer.valueOf(instanceIDAsString);
        int userID = Integer.valueOf(userIDAsString);
        return jdbi.inTransaction(handle -> {
            RoomFlowRepsitory roomFlowRepsitory = handle.attach(RoomFlowRepsitory.class);
            return roomFlowRepsitory.getUserGrades(userID, instanceID);
        });
    }
}
