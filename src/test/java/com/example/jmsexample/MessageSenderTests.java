package com.example.jmsexample;

import com.ibm.msg.client.wmq.WMQConstants;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;

import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.Session;

import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class MessageSenderTests {
    private static Logger log = LoggerFactory.getLogger(MessageSenderTests.class);

    @Mock
    JmsTemplate jmsTemplate;

    @InjectMocks
    MessageSender sender = new MessageSender();

    @Test
    public void verifyHeaders() throws Exception{
        String messageBody="test message";
        Connection connection=null;
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://embedded?broker.persistent=false,useShutdownHook=false");
        try {
            connection = factory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            connection.start();
            Message msg = session.createTextMessage(messageBody);
            sender.send("test message");
            ArgumentCaptor<MessagePostProcessor> postProcessorArgumentCaptor = ArgumentCaptor.forClass(MessagePostProcessor.class);
            Mockito.verify(jmsTemplate, Mockito.times(1)).convertAndSend(anyString(), anyString(), postProcessorArgumentCaptor.capture());
            MessagePostProcessor messagePostProcessor = postProcessorArgumentCaptor.getValue();
            Message message = messagePostProcessor.postProcessMessage(msg);
            Assert.assertEquals(273, msg.getIntProperty(WMQConstants.JMS_IBM_MQMD_ENCODING));
        }
        finally{
            connection.close();
        }
    }
}
