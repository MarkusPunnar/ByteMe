package byteMe.services;

import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface SummaryRepository extends SqlObject {

    @SqlQuery("SELECT Displayname FROM Users JOIN Roomusers ON ConnecteduserID = UserID WHERE RoomID = :roomID")
    List<String> getRoomUsernames(int roomID);


}
