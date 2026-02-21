package com.example.finCore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
public class FinCoreApplication {

	public static void main(String[] args) {
		// THIS MUST BE FIRST - before SpringApplication.run()
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		SpringApplication.run(FinCoreApplication.class, args);
	}
}