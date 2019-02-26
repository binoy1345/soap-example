package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EntityScan("io.spring.guides.gs_producing-web-service")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
