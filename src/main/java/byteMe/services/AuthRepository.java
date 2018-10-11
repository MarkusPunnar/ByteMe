package byteMe.services;

import byteMe.model.UserDAO;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface AuthRepository extends SqlObject {

    @SqlQuery("SELECT Displayname FROM Users")
    List<String> getAllUsernames();

    @SqlUpdate("INSERT INTO Users (Displayname, HashedPassword, Email)" +
            " VALUES (:displayname, :hashedPassword, :email)")
    void registerUser(@BindBean UserDAO user);

    @SqlQuery("SELECT * FROM Users WHERE Displayname = :username")
    UserDAO getUserData(String username);

    @SqlQuery("SELECT Hostname FROM Rooms WHERE RoomID = :roomID")
    int getHostIdByRoom(int roomID);

    @SqlQuery("SELECT COUNT(*) FROM Users WHERE GoogleID = :googleID")
    int getGoogleIDCount(String googleID);

    @SqlUpdate("INSERT INTO Users (GoogleID, Email) VALUES (:googleID, :email)")
    void insertNewGoogleUser(String googleID, String email);

    @SqlUpdate("UPDATE Users SET Displayname = :displayname WHERE GoogleID = :googleID")
    void setNewGoogleUserDisplayname(String googleID, String displayname);

    @SqlQuery("SELECT Displayname FROM Users WHERE GoogleID = :googleID")
    String getGoogleUserDisplayName(String googleID);

    @SqlQuery("SELECT Hashedpassword FROM Users WHERE Displayname = :displayname")
    String getPasswordByDisplayName(String displayname);
}
