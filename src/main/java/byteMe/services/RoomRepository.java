package byteMe.services;

import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface RoomRepository extends SqlObject {

    @SqlQuery("SELECT COUNT(*) FROM rooms WHERE RoomID = :roomID")
    int getRoomIDCount(int roomID);

    @SqlUpdate("INSERT INTO Rooms (RoomID, Hostname, elementAmount) VALUES (:roomID, :host, :amount")
    void addRoom(int roomID, int host, int amount);

    @SqlUpdate("INSERT INTO Elements (RoomID, ElementContent) VALUES (:roomID, :content")
    void addElement(int roomID, String content);
}
