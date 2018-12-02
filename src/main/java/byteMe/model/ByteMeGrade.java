package byteMe.model;

public class ByteMeGrade {

    private int elementID;
    private int gradeScore;
    private String deleted;

    public ByteMeGrade(int elementID, int gradeScore, String deleted) {
        this.elementID = elementID;
        this.gradeScore = gradeScore;
        this.deleted = deleted;
    }

    public int getElementID() {
        return elementID;
    }

    public int getGrade() {
        return gradeScore;
    }

    public String getDeleted() {
        return deleted;
    }
}
