package com.cacib.pqk.application.queue;

import com.cacib.pqk.application.message.MessageServiceImpl;
import com.cacib.pqk.message.Message;
import com.cacib.pqk.message.MessageServicePort;
import com.cacib.pqk.message.MessageStatus;
import com.cacib.pqk.partner.type.Direction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.jms.JMSException;
import jakarta.jms.JMSRuntimeException;
import jakarta.jms.TextMessage;
import org.apache.activemq.artemis.commons.shaded.json.JsonException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;


@Component
public class MqMessageListener {

    private final MessageServicePort messageService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MqMessageListener(MessageServiceImpl messageService) {
        this.messageService = messageService;
    }

    @JmsListener(destination = "${ibm.mq.queue}")
    public void onMessage(jakarta.jms.Message message) {
        if (message instanceof TextMessage textMessage) {
            try {
                messageService.process(createMessage(textMessage));
            } catch (JMSException exception) {
                messageService.processWithError(createErroredMessage());
                throw new JMSRuntimeException("Unexpected JMS exception while processing TextMessage:", exception.getMessage());
            }
        } else {
            throw new IllegalArgumentException("Only TextMessage supported");
        }
    }

    private Message createMessage(TextMessage textMessage) throws JMSException {
        String partnerAlias = textMessage.getStringProperty("partnerAlias");
        String metadata = createMetadata(textMessage);
        String payload = textMessage.getText();
        return Message.builder()
                .partnerAlias(partnerAlias)
                .metadata(metadata)
                .direction(Direction.INBOUND)
                .payload(payload)
                .receivedAt(LocalDateTime.now())
                .status(MessageStatus.PROCESSED)
                .build();
    }

    private Message createErroredMessage() {
        return Message.builder()
                .partnerAlias("default")
                .metadata("{}")
                .direction(Direction.INBOUND)
                .payload("empty")
                .receivedAt(LocalDateTime.now())
                .status(MessageStatus.PROCESSED)
                .build();
    }

    private String createMetadata(TextMessage textMessage) throws JMSException {
        ObjectNode json = objectMapper.createObjectNode();
        String timestamp = textMessage.getStringProperty("timestamp");
        json.put("timestamp", timestamp);
        try {
            return objectMapper.writeValueAsString(json);
        } catch (JsonProcessingException exception) {
            throw new JsonException("Unexpected Json exception while creating metadata", exception);
        }
    }
}
