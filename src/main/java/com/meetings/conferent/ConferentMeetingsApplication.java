package com.meetings.conferent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.meetings.conferent"})
public class ConferentMeetingsApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ConferentMeetingsApplication.class, args);
	}
}
