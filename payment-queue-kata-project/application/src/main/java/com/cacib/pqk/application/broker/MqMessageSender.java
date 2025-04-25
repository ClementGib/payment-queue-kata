package com.cacib.pqk.application.broker;

import com.cacib.pqk.domain.message.Message;
import com.cacib.pqk.domain.partner.Partner;
import com.ibm.mq.jakarta.jms.MQQueueConnectionFactory;
import jakarta.jms.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MqMessageSender implements MessageSender {

    Logger logger = LoggerFactory.getLogger(MqMessageSender.class);

public void sendMessage(Message outboundMessage, Partner receiverPartner) throws JMSException {
    BrokerConfig brokerConfig = new BrokerConfig(receiverPartner.getApplication());
    MQQueueConnectionFactory factory = brokerConfig.factory();

    try (
            QueueConnection connection = factory.createQueueConnection(brokerConfig.getUser(), brokerConfig.getPassword());
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE)
    ) {
        Queue queue = session.createQueue("queue:///" + brokerConfig.getQueueName());
        QueueSender sender = session.createSender(queue);
        TextMessage sendingMessage = session.createTextMessage(outboundMessage.getPayload());
        sendingMessage.setStringProperty("partnerAlias", outboundMessage.getReceiverAlias());
        sendingMessage.setLongProperty("timestamp", System.currentTimeMillis());
        sender.send(sendingMessage);
        logger.info("Sent message to " + outboundMessage.getReceiverAlias());
    }
}
}
