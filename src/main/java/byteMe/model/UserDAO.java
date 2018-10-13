package byteMe.model;

public class UserDAO {

    private Integer userID;
    private String displayname;
    private String hashedPassword;
    private String email;

    public UserDAO(Integer userID, String displayname, String hashedPassword, String email) {
        this.userID = userID;
        this.displayname = displayname;
        this.hashedPassword = hashedPassword;
        this.email = email;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
