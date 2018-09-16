package byteMe;

import byteMe.services.RoomRepository;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ByteMeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ByteMeApplication.class, args);
    }

    @Bean
    Jdbi jdbi() {
        Jdbi jdbi = Jdbi.create("jdbc:mysql://localhost:3306/byteme", "root", "root");
        jdbi.installPlugin(new SqlObjectPlugin());
        return jdbi;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    RoomRepository roomRepository() {
        return jdbi().open().attach(RoomRepository.class);
}
}
