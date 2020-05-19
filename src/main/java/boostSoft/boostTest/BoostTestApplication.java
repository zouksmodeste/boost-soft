package boostSoft.boostTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import boostSoft.boostTest.service.impl.UserServiceImpl;

@SpringBootApplication
public class BoostTestApplication {
	
	@Autowired UserServiceImpl userServiceImpl;

	public static void main(String[] args) {
		SpringApplication.run(BoostTestApplication.class, args);
	}
}
