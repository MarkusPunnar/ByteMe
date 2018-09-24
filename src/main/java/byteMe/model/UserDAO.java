package byteMe.model;

public class UserDAO {

    private Integer userID;
    private String username;
    private String hashedPassword;
    private String userEmail;
    private String userRole;

    public UserDAO(Integer userID, String username, String hashedPassword, String userEmail, String userRole) {
        this.userID = userID;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.userEmail = userEmail;
        this.userRole = userRole;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
