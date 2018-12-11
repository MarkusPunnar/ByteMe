package byteMe.controllers;

import byteMe.model.ByteMeElement;
import byteMe.model.ByteMeGrade;
import byteMe.model.UserDAO;
import byteMe.services.RoomFlowRepsitory;
import byteMe.services.SummaryRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
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
    public List<UserDAO> getUsernamesByRoom(@PathVariable("instanceID") String instanceIDAsString) {
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
    @RequestMapping("/grades/{instanceID}/{userID}")
    public List<ByteMeGrade> getGradesByRoomUser(@PathVariable("instanceID") String instanceIDAsString, @PathVariable("userID") String userIDAsString) {
        int instanceID = Integer.valueOf(instanceIDAsString);
        int userID = Integer.valueOf(userIDAsString);
        return jdbi.inTransaction(handle -> {
            SummaryRepository summaryRepository = handle.attach(SummaryRepository.class);
            return summaryRepository.getUserGrades(userID, instanceID);
        });
    }

    @ResponseBody
    @RequestMapping("/elementdetails/{elementID}")
    public List<String> getElementDetails(@PathVariable("elementID") String elementIDAsString) {
        List<String> elementDetails = new ArrayList<>();
        Integer elementID = Integer.valueOf(elementIDAsString);
        return jdbi.inTransaction(handle -> {
            SummaryRepository summaryRepository = handle.attach(SummaryRepository.class);
            Integer gradeCount = summaryRepository.getElementGradeCount(elementID);
            elementDetails.add(gradeCount.toString());
            double average = summaryRepository.getElementAvgGrade(elementID);
            elementDetails.add(Double.toString(average));
            String maxUser = summaryRepository.getMaxGradeUser(elementID);
            elementDetails.add(maxUser);
           return elementDetails;
        });
    }
}
