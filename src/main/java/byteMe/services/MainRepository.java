package byteMe.services;

import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface MainRepository extends SqlObject {

    @SqlQuery("SELECT COUNT(*) FROM Users")
    int getUserCount();
}
