package com.example.jmsexample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JmsActiveMqApplication.class})
@ContextConfiguration(classes={TestConfig.class})
@DirtiesContext
public class JmsActiveMqIntTests {
	private static Logger log = LoggerFactory.getLogger(JmsActiveMqIntTests.class);

	@Autowired
	MessageSender sender;

	@Test
	public void shouldReceiveSuccessfully() {
		sender.send("This is test message");
	}


}
