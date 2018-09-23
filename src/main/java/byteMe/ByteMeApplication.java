package byteMe;

import byteMe.model.UserDAO;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ByteMeApplication {

    @Value("${spring.datasource.username}")
    private String dbUser;
    @Value("${spring.datasource.password}")
    private String dbPassword;

    public static void main(String[] args) {
        SpringApplication.run(ByteMeApplication.class, args);
    }

    @Bean
    Jdbi jdbi() {
        Jdbi jdbi = Jdbi.create("jdbc:mysql://localhost:3306/byteme", dbUser, dbPassword);
        jdbi.registerRowMapper(ConstructorMapper.factory(UserDAO.class));
        jdbi.installPlugin(new SqlObjectPlugin());
        return jdbi;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
