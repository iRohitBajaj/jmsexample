package com.example.jmsexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JmsActiveMqApplication implements ApplicationRunner {

	@Autowired
	MessageSender sender;

	public static void main(String[] args) {
		SpringApplication.run(JmsActiveMqApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		//Message<?> message = MessageBuilder.withPayload("This is test message").build();

		sender.send("This is test message");
	}
}
