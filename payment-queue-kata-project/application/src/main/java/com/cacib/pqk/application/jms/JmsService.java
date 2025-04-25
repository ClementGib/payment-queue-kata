package com.cacib.pqk.application.jms;

import com.cacib.pqk.domain.message.Message;
import com.cacib.pqk.domain.message.MessageStatus;
import com.cacib.pqk.domain.partner.type.Direction;
import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import org.apache.activemq.artemis.commons.shaded.json.JsonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class JmsService {

    public static final String EMITTER_ALIAS = "emitterAlias";
    public static final String RECEIVER_ALIAS = "receiverAlias";
    Logger logger = LoggerFactory.getLogger(JmsService.class);

    public Message extractMessage(TextMessage textMessage) throws JMSException {
        String emitterAlias = textMessage.getStringProperty(EMITTER_ALIAS);
        String receiverAlias = textMessage.getStringProperty(RECEIVER_ALIAS);
        String payload = textMessage.getText();
        Map<String, String> metadata = createMetadata(textMessage);
        return Message.builder()
                .emitterAlias(emitterAlias)
                .receiverAlias(receiverAlias)
                .metadata(metadata)
                .direction(Direction.INBOUND)
                .payload(payload)
                .receivedAt(LocalDateTime.now())
                .status(MessageStatus.IN_PROGRESS)
                .build();
    }

    private Map<String, String>  createMetadata(TextMessage textMessage) throws JMSException {
        Map<String, String> metadata = new HashMap<>();
        String timestamp = textMessage.getStringProperty("timestamp");
        metadata.put("timestamp", timestamp);
        return metadata;
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

        Map<String, String> metadata = new HashMap<>();

        metadata.put("jmsMessageID", safe(textMessage::getJMSMessageID));
        Long timestamp = safe(textMessage::getJMSTimestamp);
        metadata.put("timestamp", timestamp != null ? timestamp.toString() : null);
        metadata.put("type", safe(textMessage::getJMSType));
        metadata.put("destination", safe(() -> String.valueOf(textMessage.getJMSDestination())));
        metadata.put("error", rootCause.getMessage() != null ? rootCause.getMessage().replace("\"", "'") : "Unknown error");


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
