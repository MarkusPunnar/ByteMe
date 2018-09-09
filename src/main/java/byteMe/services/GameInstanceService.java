package byteMe.services;


import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class GameInstanceService {

    public int generateInstanceID() {
        return 100000 + ThreadLocalRandom.current().nextInt(900000);
    }


}
