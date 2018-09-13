package byteMe;

import org.jdbi.v3.core.Jdbi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ByteMeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ByteMeApplication.class, args);
    }

    @Bean
    Jdbi jdbi() {
        return Jdbi.create("jdbc:mysql://localhost:3306/byteme", "root", "root");
    }
}
