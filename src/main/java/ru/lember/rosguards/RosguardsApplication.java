package ru.lember.rosguards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RosguardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RosguardsApplication.class, args);
	}

}
