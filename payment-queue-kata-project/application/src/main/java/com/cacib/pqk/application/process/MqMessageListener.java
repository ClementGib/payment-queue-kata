package com.cacib.pqk.application.process;

import com.cacib.pqk.application.jms.JmsService;
import com.cacib.pqk.message.Message;
import com.cacib.pqk.message.MessageServicePort;
import jakarta.jms.JMSException;
import jakarta.jms.JMSRuntimeException;
import jakarta.jms.TextMessage;
import org.apache.activemq.artemis.commons.shaded.json.JsonException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MqMessageListener {

    private final JmsService jmsService;
    private final MessageServicePort messageService;


    public MqMessageListener(JmsService jmsService, MessageServicePort messageService) {
        this.jmsService = jmsService;
        this.messageService = messageService;
    }

    @JmsListener(destination = "${ibm.mq.queue}")
    public void onMessage(jakarta.jms.Message jmsMessage) {
        if (jmsMessage instanceof TextMessage textMessage) {
            Message message;
            try {
                message = jmsService.extractMessage(textMessage);
                messageService.process(message);
            } catch (JMSException | JsonException exception) {
                message = jmsService.createErroredMessage(textMessage, exception);
                messageService.processWithError(message);
                throw new JMSRuntimeException("Unexpected JMS exception while processing TextMessage:", exception.getMessage());
            }
        } else {
            throw new IllegalArgumentException("Only TextMessage supported");
        }
    }
}
