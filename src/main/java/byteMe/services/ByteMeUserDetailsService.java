package byteMe.services;

import byteMe.model.UserDAO;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ByteMeUserDetailsService implements UserDetailsService {


    private Jdbi jdbi;

    @Autowired
    public ByteMeUserDetailsService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Set<GrantedAuthority> userRoles = new HashSet<>();
        return jdbi.inTransaction(handle -> {
            AuthRepository authRepository = handle.attach(AuthRepository.class);
            UserDAO userInfo = authRepository.getUserData(username);
            if (userInfo == null) {
                throw new UsernameNotFoundException("User was not found");
            }
            userRoles.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new User(userInfo.getDisplayname(), userInfo.getHashedPassword(), userRoles);
        });
    }
}
