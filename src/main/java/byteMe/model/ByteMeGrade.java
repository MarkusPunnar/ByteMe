package byteMe.model;

public class ByteMeGrade {

    private int gradeScore;
    private int elementID;
    private int userID;
    private int roomID;
    private String deleted;

    public ByteMeGrade(int gradeScore, int elementID, int userID, int roomID, String deleted) {
        this.elementID = elementID;
        this.gradeScore = gradeScore;
        this.userID = userID;
        this.roomID = roomID;
        this.deleted = deleted;
    }

    public int getElementID() {
        return elementID;
    }

    public void setElementID(int elementID) {
        this.elementID = elementID;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public int getGradeScore() {
        return gradeScore;
    }

    public void setGradeScore(int gradeScore) {
        this.gradeScore = gradeScore;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }
}
