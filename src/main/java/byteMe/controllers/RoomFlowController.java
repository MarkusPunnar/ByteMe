package byteMe.controllers;

import byteMe.model.ByteMeElement;
import byteMe.services.AuthRepository;
import byteMe.services.RoomFlowRepsitory;
import byteMe.services.RoomStartupRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/room")
public class RoomFlowController {

    private Jdbi jdbi;

    @Autowired
    public RoomFlowController(Jdbi jdbi) {
        this.jdbi = jdbi;
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
            List<ByteMeElement> elementList = roomFlowRepsitory.getAllElementsByRoom(instanceID);
            if (elementNumber > elementList.size()) {
                return "redirect:/room/" + instanceIDAsString + "/summary";
            }
            ByteMeElement currentElement = elementList.get(elementNumber - 1);
            model.addAttribute("elementContent", currentElement.getElementContent());
            model.addAttribute("elementID", currentElement.getElementID());
            return "display";
        });
    }

    @RequestMapping(value = "/{instanceID}/gradeElement/{elementNumber}", method = RequestMethod.POST)
    public String gradeElement(@PathVariable("instanceID") String instanceIDAsString,
                               @PathVariable("elementNumber") String elementNumberAsString,
                               @ModelAttribute("grade") Integer grade,
                               @ModelAttribute("current") String elementIDAsString) {
        int elementNumber = Integer.valueOf(elementNumberAsString);
        int instanceID = Integer.valueOf(instanceIDAsString);
        int elementID = Integer.valueOf(elementIDAsString);
        return jdbi.inTransaction(handle -> {
            RoomFlowRepsitory roomFlowRepsitory = handle.attach(RoomFlowRepsitory.class);
            roomFlowRepsitory.saveGrade(instanceID, elementID, grade);
            return "redirect:/room/" + instanceIDAsString + "/displayElement/" + (elementNumber + 1);
        });
    }

    @RequestMapping("/{instanceID}/summary")
    public String showSummary(@PathVariable("instanceID") String instanceIDAsString, Model model) {
        Integer instanceID = Integer.parseInt(instanceIDAsString);
        return jdbi.inTransaction(handle -> {
            RoomFlowRepsitory roomFlowRepsitory = handle.attach(RoomFlowRepsitory.class);
            RoomStartupRepository roomRepository = handle.attach(RoomStartupRepository.class);
            String hostname = roomRepository.getHostName(instanceID);
            int elementCount = roomFlowRepsitory.getAllElementsByRoom(instanceID).size();
            model.addAttribute("hostname", hostname);
            model.addAttribute("elementCount", elementCount);
            return "summary";
        });
    }
}
