package song.teamo3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Teamo3Application {

	public static void main(String[] args) {
		SpringApplication.run(Teamo3Application.class, args);
	}

}
