package byteMe.services;


import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class GameInstanceService {

    private Jdbi jdbi;

    @Autowired
    public GameInstanceService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public int generateInstanceID() {
        return 100000 + ThreadLocalRandom.current().nextInt(900000);
    }

    public int checkForDuplicateRoomID(int instanceId) {
        return jdbi.inTransaction(handle -> handle.createQuery("SELECT COUNT(*) FROM rooms WHERE RoomID = " + instanceId)
                .mapTo(int.class).findOnly());
    }
}
