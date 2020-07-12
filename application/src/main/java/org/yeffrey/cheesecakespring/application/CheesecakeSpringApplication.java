package org.yeffrey.cheesecakespring.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.yeffrey.cheesecakespring")
public class CheesecakeSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheesecakeSpringApplication.class, args);
	}

}
