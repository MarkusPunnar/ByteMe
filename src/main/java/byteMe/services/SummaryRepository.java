package byteMe.services;

import byteMe.model.ByteMeGrade;
import byteMe.model.UserDAO;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface SummaryRepository extends SqlObject {

    @SqlQuery("SELECT * FROM Users JOIN Roomusers ON ConnecteduserID = UserID WHERE RoomID = :roomID")
    List<UserDAO> getRoomUsernames(int roomID);

    @SqlQuery("SELECT * FROM Grades WHERE RoomID = :roomID AND UserID = :userID")
    List<ByteMeGrade> getUserGrades(int userID, int roomID);

    @SqlQuery("SELECT COUNT(*) FROM Grades WHERE ElementID = :elementID AND Deleted = \"N\"")
    Integer getElementGradeCount(int elementID);

    @SqlQuery("SELECT AVG(GradeScore) FROM Grades WHERE ElementID = :elementID AND Deleted = \"N\"")
    double getElementAvgGrade(int elementID);

    @SqlQuery("SELECT Displayname FROM Users JOIN Grades ON Grades.UserID = Users.UserID WHERE ElementID = :elementID AND GradeScore IN (SELECT MAX(GradeScore) FROM Grades WHERE ElementID = :elementID)")
    String getMaxGradeUser(int elementID);

}
