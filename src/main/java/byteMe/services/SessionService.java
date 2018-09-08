package byteMe.services;


import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class SessionService {

    public int generateSessionID() {
        return 100000 + ThreadLocalRandom.current().nextInt(900000);
    }


}
