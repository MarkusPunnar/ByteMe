package byteMe.model;

import java.util.List;

public class RoomUserDataStore {

    private int userCount;
    private List<String> usernames;

    public RoomUserDataStore(int userCount, List<String> usernames) {
        this.userCount = userCount;
        this.usernames = usernames;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }
}
