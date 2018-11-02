package byteMe.services;

import byteMe.model.ByteMeElement;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface RoomFlowRepsitory extends SqlObject {

    @SqlQuery("SELECT * FROM Elements WHERE RoomID = :roomID")
    List<ByteMeElement> getAllElementsByRoom(Integer roomID);

    @SqlUpdate("INSERT INTO Grades (RoomID, ElementID, GradeScore, UserID) VALUES(:roomID, :elementID, :grade, :userID)")
    void saveGrade(int roomID, int elementID, int grade, int userID);
}
