package byteMe.model;

import java.util.List;
import java.util.Map;

public class Session {

    private int id;
    private List<String> elements;
    private Map<Integer, List<Integer>> answers;

    public Session(int id, List<String> elements, Map<Integer, List<Integer>> answers) {
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

    public List<String> getElements() {
        return elements;
    }

    public void setElements(List<String> elements) {
        this.elements = elements;
    }

    public Map<Integer, List<Integer>> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Integer, List<Integer>> answers) {
        this.answers = answers;
    }
}
