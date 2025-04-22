package com.cacib.pqk.mq;


import com.ibm.mq.jakarta.jms.MQQueueConnectionFactory;
import jakarta.jms.*;

public class MqSender {

    public static void main(String[] args) throws Exception {
        MqConfig config = new MqConfig();
        MQQueueConnectionFactory factory = config.factory();

        try (
                QueueConnection connection = factory.createQueueConnection(config.getUser(), config.getPassword());
                QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE)
        ) {
            Queue queue = session.createQueue("queue:///" + config.getQueueName());
            QueueSender sender = session.createSender(queue);
            String payload = """
                    {
                      "type": "MESSAGE",
                      "header": "FOR IT ERP TEAM",
                      "body": "Hello from mq-tester: new deal to book from CRM"
                    }
                    """;
            TextMessage message = session.createTextMessage(payload);
            message.setStringProperty("partnerAlias", "crm_to_pqk");
            message.setLongProperty("timestamp", System.currentTimeMillis());
            sender.send(message);
            System.out.println("✅ Message sent to queue: " + config.getQueueName());
        }
    }
}
