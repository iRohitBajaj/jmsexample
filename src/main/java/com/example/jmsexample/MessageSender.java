package com.example.jmsexample;

import com.ibm.msg.client.wmq.WMQConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.DeliveryMode;
import java.util.UUID;

import static com.example.jmsexample.JmsConfig.TEMP_QUEUE;

@Component
public class MessageSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    private static Logger log = LoggerFactory.getLogger(MessageSender.class);

    public void send(String myMessage) {
        System.out.println("sending with convertAndSend() to queue <" + myMessage + ">");
        jmsTemplate.convertAndSend(TEMP_QUEUE, myMessage, m -> {

            log.info("setting standard JMS headers before sending");
            m.setJMSCorrelationID(UUID.randomUUID().toString());
            m.setJMSExpiration(1000);
            m.setJMSMessageID("message-id");
            m.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
            m.setJMSTimestamp(System.nanoTime());

            log.info("setting custom JMS headers before sending");
            m.setStringProperty(WMQConstants.JMS_IBM_MQMD_APPLIDENTITYDATA, "app idddddentifier");
            m.setIntProperty(WMQConstants.JMS_IBM_MQMD_ENCODING,273);

            return m;
        });
    }
}
