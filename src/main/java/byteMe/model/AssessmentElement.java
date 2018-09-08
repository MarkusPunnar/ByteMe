package byteMe.model;

public class AssessmentElement {

    private int elementNumber;
    private String elementContent;

    public AssessmentElement(int elementNumber, String elementContent) {
        this.elementNumber = elementNumber;
        this.elementContent = elementContent;
    }

    public int getElementNumber() {
        return elementNumber;
    }

    public void setElementNumber(int elementNumber) {
        this.elementNumber = elementNumber;
    }

    public String getElementContent() {
        return elementContent;
    }

    public void setElementContent(String elementContent) {
        this.elementContent = elementContent;
    }
}
