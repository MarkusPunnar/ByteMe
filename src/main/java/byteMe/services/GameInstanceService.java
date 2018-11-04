package byteMe.services;


import org.jdbi.v3.core.Jdbi;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class GameInstanceService {

    private Map<Integer, Boolean> roomStatusStore = new HashMap<>();

    public int generateInstanceID() {
        return 100000 + ThreadLocalRandom.current().nextInt(900000);
    }

    public String getCurrentUsername(Jdbi jdbi) {
        Object userObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userObject instanceof UserDetails) {
            return ((UserDetails) userObject).getUsername();
        } else if (userObject instanceof DefaultOidcUser) {
            return jdbi.inTransaction(handle -> {
                AuthRepository authRepository = handle.attach(AuthRepository.class);
                String googleID = ((DefaultOidcUser) userObject).getName();
                return authRepository.getGoogleUserDisplayName(googleID);
            });
        }
        return null;
    }

    public Map<Integer, Boolean> getRoomStatusStore() {
        return roomStatusStore;
    }

    public String formatTimeFromCreation(int secondsFromCreation) {
        int hours = secondsFromCreation / 3600;
        int minutes = (secondsFromCreation % 3600) / 60;
        int seconds = secondsFromCreation % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public int getTotalRoomElements(List<String> textList, List<MultipartFile> fileList) {
        if (textList == null) {
            return fileList.size();
        }
        if (fileList == null) {
            return textList.size();
        }
        return fileList.size() + textList.size();
    }
}
