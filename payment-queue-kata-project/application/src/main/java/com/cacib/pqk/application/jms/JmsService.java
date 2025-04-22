package com.cacib.pqk.application.jms;

import com.cacib.pqk.message.Message;
import com.cacib.pqk.message.MessageStatus;
import com.cacib.pqk.partner.type.Direction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import org.apache.activemq.artemis.commons.shaded.json.JsonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class JmsService {

    public static final String EMITTER_ALIAS = "emitterAlias";
    public static final String RECEIVER_ALIAS = "receiverAlias";
    Logger logger = LoggerFactory.getLogger(JmsService.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Message extractMessage(TextMessage textMessage) throws JMSException {
        String emitterAlias = textMessage.getStringProperty(EMITTER_ALIAS);
        String receiverAlias = textMessage.getStringProperty(RECEIVER_ALIAS);
        String payload = textMessage.getText();
        String metadata = createMetadata(textMessage);
        return Message.builder()
                .emitterAlias(emitterAlias)
                .receiverAlias(receiverAlias)
                .metadata(metadata)
                .direction(Direction.INBOUND)
                .payload(payload)
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

    public Message createErroredMessage(TextMessage textMessage, Exception rootCause) {
        String emitterAlias = null;
        String receiverAlias = null;

        try {
            emitterAlias = textMessage.getStringProperty(EMITTER_ALIAS);
        } catch (JMSException | JsonException exception) {
            logger.error(EMITTER_ALIAS + "is missing.");
        }

        try {
            receiverAlias = textMessage.getStringProperty(RECEIVER_ALIAS);
        } catch (JMSException | JsonException exception) {
            logger.error(RECEIVER_ALIAS + "is missing.");
        }

        String metadata = """
                {
                    "jmsMessageID": "%s",
                    "timestamp": %d,
                    "type": "%s",
                    "destination": "%s",
                    "error": "%s"
                }
                """.formatted(
                safe(textMessage::getJMSMessageID),
                safe(textMessage::getJMSTimestamp),
                safe(textMessage::getJMSType),
                safe(() -> String.valueOf(textMessage.getJMSDestination())),
                rootCause.getMessage().replace("\"", "'")
        );

        return Message.builder()
                .emitterAlias(emitterAlias != null ? emitterAlias : "unknown")
                .receiverAlias(receiverAlias != null ? receiverAlias : "unknown")
                .metadata(metadata)
                .direction(Direction.INBOUND)
                .payload("UNREADABLE_MESSAGE")
                .receivedAt(LocalDateTime.now())
                .status(MessageStatus.ERROR)
                .build();

    }

    private <T> T safe(JMSCallable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            return null;
        }
    }
}
