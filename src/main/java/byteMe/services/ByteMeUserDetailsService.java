package byteMe.services;

import byteMe.model.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service

public class ByteMeUserDetailsService implements UserDetailsService {

    private AuthRepository authRepository;

    @Autowired
    public ByteMeUserDetailsService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Set<GrantedAuthority> userRoles = new HashSet<>();
        UserDAO userInfo = authRepository.getUserData(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException("User was not found");
        }
        switch (userInfo.getUserRole()) {
            case "user":
                userRoles.add(new SimpleGrantedAuthority("ROLE_USER"));
                break;
            case "admin":
                userRoles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                userRoles.add(new SimpleGrantedAuthority("ROLE_USER"));
                break;
        }
        return new User(userInfo.getUsername(), userInfo.getHashedPassword(), userRoles);
    }
}
