package byteMe.services;

import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface InstanceRepository extends SqlObject {

    @SqlQuery("SELECT COUNT(*) FROM Rooms WHERE RoomID = :roomID")
    int getRoomIDCount(int roomID);

    @SqlUpdate("INSERT INTO Rooms (RoomID, Hostname, elementAmount) VALUES (:roomID, :host, :amount)")
    void addRoom(int roomID, int host, int amount);

    @SqlUpdate("INSERT INTO Elements (RoomID, ElementContent) VALUES (:roomID, :content)")
    void addElement(int roomID, String content);

    @SqlQuery("SELECT username FROM Users JOIN Rooms ON Rooms.Hostname = Users.userID AND Rooms.roomID = :roomID")
    String getHostName(int roomID);

}
