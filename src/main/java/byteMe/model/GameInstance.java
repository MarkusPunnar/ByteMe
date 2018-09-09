package byteMe.model;

import java.util.List;
import java.util.Map;

public class GameInstance {

    private int id;
    private List<AssessmentElement> elements;
    private Map<Integer, List<Integer>> answers;

    public GameInstance(int id, List<AssessmentElement> elements, Map<Integer, List<Integer>> answers) {
        this.id = id;
        this.elements = elements;
        this.answers = answers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<AssessmentElement> getElements() {
        return elements;
    }

    public void setElements(List<AssessmentElement> elements) {
        this.elements = elements;
    }

    public Map<Integer, List<Integer>> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Integer, List<Integer>> answers) {
        this.answers = answers;
    }
}
