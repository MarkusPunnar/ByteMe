package byteMe.services;

import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface RoomStartupRepository extends SqlObject {

    @SqlQuery("SELECT COUNT(*) FROM Rooms WHERE RoomID = :roomID")
    int getRoomIDCount(int roomID);

    @SqlUpdate("INSERT INTO Rooms (RoomID, Hostname, elementAmount) VALUES (:roomID, :host, :amount)")
    void addRoom(int roomID, int host, int amount);

    @SqlUpdate("INSERT INTO Elements (RoomID, ElementContent, ElementType) VALUES (:roomID, :content, \"text\")")
    void addElement(int roomID, String content);

    @SqlQuery("SELECT username FROM Users JOIN Rooms ON Rooms.Hostname = Users.userID AND Rooms.roomID = :roomID")
    String getHostName(int roomID);

    @SqlQuery("SELECT UserID FROM Users WHERE username = :username")
    int getUserID(String username);

    @SqlUpdate("INSERT INTO Roomusers (RoomID, ConnecteduserID) VALUES (:roomID, :userID)")
    void addUserToRoom(int roomID, int userID);

    @SqlQuery("SELECT username FROM Users JOIN Roomusers ON" +
            " Roomusers.connectedUserid = Users.userID WHERE Roomusers.RoomID = :roomID")
    List<String> getRoomConnectedUsers(int roomID);

}
