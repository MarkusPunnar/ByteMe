package byteMe.services;

import byteMe.model.ByteMeElement;
import byteMe.model.ByteMeGrade;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface RoomFlowRepsitory extends SqlObject {

    @SqlQuery("SELECT * FROM Elements WHERE RoomID = :roomID")
    List<ByteMeElement> getAllElementsByRoom(Integer roomID);

    @SqlUpdate("INSERT INTO Grades (RoomID, ElementID, GradeScore, UserID, Deleted) VALUES(:roomID, :elementID, :grade, :userID, \"N\")")
    void saveGrade(int roomID, int elementID, int grade, int userID);

    @SqlQuery("SELECT TIMESTAMPDIFF(SECOND, (SELECT creationDate FROM Rooms WHERE RoomID = :roomID), NOW())")
    int getSecondsFromCreation(int roomID);

    @SqlQuery("SELECT COUNT(DISTINCT UserID) FROM Grades WHERE RoomID = :roomID")
    int getGradedUserCount(int roomID);

    @SqlUpdate("UPDATE Grades SET Deleted = \"Y\" WHERE UserID = :userID AND RoomID = :roomID")
    void deleteUserData(int userID, int roomID);

    @SqlUpdate("DELETE FROM Roomusers WHERE ConnecteduserID = :userID AND RoomID = :roomID")
    void deleteUserFromRoom(int userID, int roomID);

    @SqlUpdate("UPDATE Grades SET Deleted = \"Y\" WHERE UserID = :userID AND ElementID = :elementID")
    void deleteUserGrade(int userID, int elementID);

    @SqlUpdate("UPDATE Grades SET GradeScore = :grade WHERE UserID = :userID AND ElementID = :elementID")
    void editGrade(int grade, int elementID, int userID);

    @SqlQuery("SELECT * FROM Grades WHERE RoomID = :roomID AND UserID = :userID")
    List<ByteMeGrade> getUserGrades(int userID, int roomID);

    @SqlQuery("SELECT COUNT(*) FROM Grades WHERE UserID = :userID AND ElementID = :elementID")
    int getUserGradeCount(int userID, int elementID);
}
