package byteMe.model;

public class ByteMeGrade {

    private int gradeScore;
    private int userID;
    private int roomID;

    public ByteMeGrade(int gradeScore, int userID, int roomID) {
        this.gradeScore = gradeScore;
        this.userID = userID;
        this.roomID = roomID;
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
