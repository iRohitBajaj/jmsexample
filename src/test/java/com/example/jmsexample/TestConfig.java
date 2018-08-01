package com.example.jmsexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;
import javax.jms.Session;

import static com.example.jmsexample.JmsConfig.TEMP_QUEUE;

@Configuration
@EnableJms
public class TestConfig {

    private static Logger log = LoggerFactory.getLogger(TestConfig.class);


    @Bean(name="jmsListenerContainerFactory")
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                      DefaultJmsListenerContainerFactoryConfigurer configurer
                                                                        , MessageConverter messageConverter) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setMessageConverter(messageConverter);
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, connectionFactory);
        // You could still override some of Boot's default if necessary.
        return factory;
    }

    @Bean
    public MessageConsumer consumer(){
        MessageConsumer consumer = new MessageConsumer();
        return consumer;
    }


    @Component
    class MessageConsumer{

        @JmsListener(destination = TEMP_QUEUE)
        public void receiveMessage(@Payload String msg,
                                   @Headers MessageHeaders headers,
                                   Message message, Session session) {
            log.info("received <" + msg + ">");

            log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
            log.info("######          Message Details           #####");
            log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
            log.info("headers: " + headers);
            log.info("message: " + message);
            log.info("session: " + session);
            log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        }
    }
}
