package byteMe.services;

import byteMe.model.UserDAO;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface AuthRepository extends SqlObject {

    @SqlQuery("SELECT username FROM Users")
    List<String> getAllUsernames();

    @SqlUpdate("INSERT INTO Users (username, hashedPassword, useremail, userrole)" +
            " VALUES (:username, :hashedPassword, :email, :role)")
    void registerUser(String username, String hashedPassword, String email, String role);

    @SqlQuery("SELECT * FROM Users WHERE username = :username")
    UserDAO getUserData(String username);
}
