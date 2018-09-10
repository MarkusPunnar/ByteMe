package byteMe.controllers;


import byteMe.model.AssessmentElement;
import byteMe.model.GameInstance;
import byteMe.services.GameInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/session")
public class InstanceController {

    private GameInstanceService instanceService;
    private Map<Integer, GameInstance> instanceCollection = new HashMap<>();

    @Autowired
    public InstanceController(GameInstanceService instanceService) {
        this.instanceService = instanceService;
    }

    @PostMapping("/create")
    public String createRoom(@RequestParam("assessment") List<String> instanceElements, Model model) {
        List<AssessmentElement> orderedInstanceElements = new ArrayList<>();
        for (int i = 0; i < instanceElements.size(); i++) {
            AssessmentElement newElement = new AssessmentElement(i, instanceElements.get(i));
            orderedInstanceElements.add(newElement);
        }
        int instanceId = instanceService.generateInstanceID();
        while (instanceCollection.keySet().contains(instanceId)) {
            instanceId = instanceService.generateInstanceID();
        }
        GameInstance newGameInstance = new GameInstance(instanceId, orderedInstanceElements, new HashMap<>());
        instanceCollection.put(instanceId, newGameInstance);
        model.addAttribute("instanceId", instanceId);
        return "hostwait";
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public String joinRoom(@ModelAttribute("id") String instanceIDAsString) {
        int instanceID = Integer.valueOf(instanceIDAsString);
        if (!instanceCollection.keySet().contains(instanceID)) {
            return "redirect:/join?error";
        }
        return "redirect:/session/waitingroom/" + instanceIDAsString;
    }

    @RequestMapping("/waitingroom/{instanceID}")
    public String waitingRoom(@PathVariable("instanceID") String instanceIDAsString) {
        return "waitingroom";
    }
}
