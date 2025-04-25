package com.cacib.pqk.application.broker;

import com.cacib.pqk.application.jms.JmsService;
import com.cacib.pqk.domain.message.Message;
import com.cacib.pqk.domain.message.MessageStatus;
import com.cacib.pqk.domain.partner.type.Direction;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import static com.cacib.pqk.application.jms.JmsService.EMITTER_ALIAS;
import static com.cacib.pqk.application.jms.JmsService.RECEIVER_ALIAS;
import static com.ibm.msg.client.commonservices.trace.Trace.entry;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JmsServiceTest {

    private final JmsService jmsService = new JmsService();

    @Test
    void shouldExtractMessageSuccessfully() throws Exception {
        TextMessage textMessage = mock(TextMessage.class);
        when(textMessage.getStringProperty(EMITTER_ALIAS)).thenReturn("crm");
        when(textMessage.getStringProperty(RECEIVER_ALIAS)).thenReturn("front");
        when(textMessage.getText()).thenReturn("{\"event\":\"payment.created\"}");

        Message result = jmsService.extractMessage(textMessage);

        assertEquals("crm", result.getEmitterAlias());
        assertEquals("front", result.getReceiverAlias());
        assertEquals("{\"event\":\"payment.created\"}", result.getPayload());
        assertEquals(Direction.INBOUND, result.getDirection());
        assertEquals(MessageStatus.IN_PROGRESS, result.getStatus());
        assertNotNull(result.getReceivedAt());
        assertNotNull(result.getMetadata());
    }

    @Test
    void shouldCreateErroredMessageFromTextMessage() throws Exception {
        // Given
        TextMessage textMessage = mock(TextMessage.class);
        when(textMessage.getStringProperty(EMITTER_ALIAS)).thenReturn("crm");
        when(textMessage.getStringProperty(RECEIVER_ALIAS)).thenReturn("front");
        when(textMessage.getJMSMessageID()).thenReturn("ID123");
        when(textMessage.getJMSTimestamp()).thenReturn(1680000000000L);
        when(textMessage.getJMSType()).thenReturn("Text");
        Destination destination = mock(Destination.class);
        when(destination.toString()).thenReturn("queue://DEV.QUEUE.1");
        when(textMessage.getJMSDestination()).thenReturn(destination);

        Exception rootCause = new Exception("Simulated error");

        // When
        Message result = jmsService.createErroredMessage(textMessage, rootCause);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getEmitterAlias()).isEqualTo("crm");
        assertThat(result.getReceiverAlias()).isEqualTo("front");
        assertThat(result.getPayload()).isEqualTo("UNREADABLE_MESSAGE");
        assertThat(result.getDirection()).isEqualTo(Direction.INBOUND);
        assertThat(result.getStatus()).isEqualTo(MessageStatus.ERROR);

        assertThat(result.getMetadata())
                .asInstanceOf(InstanceOfAssertFactories.map(String.class, String.class))
                .containsEntry("jmsMessageID", "ID123")
                .containsEntry("timestamp", "1680000000000")
                .containsEntry("type", "Text")
                .containsEntry("destination", "queue://DEV.QUEUE.1")
                .containsEntry("error", "Simulated error");
    }

    @Test
    void shouldCreateErroredMessageFromTextMessageWithMissingEmitterAlias() throws Exception {
        // Given
        TextMessage textMessage = mock(TextMessage.class);
        when(textMessage.getStringProperty(EMITTER_ALIAS)).thenThrow(new JMSException(EMITTER_ALIAS + " missing"));
        when(textMessage.getStringProperty(RECEIVER_ALIAS)).thenReturn("front");
        when(textMessage.getJMSMessageID()).thenReturn("ID123");
        when(textMessage.getJMSTimestamp()).thenReturn(1680000000000L);
        when(textMessage.getJMSType()).thenReturn("Text");
        Destination destination = mock(Destination.class);
        when(destination.toString()).thenReturn("queue://DEV.QUEUE.1");
        when(textMessage.getJMSDestination()).thenReturn(destination);

        Exception rootCause = new Exception("Simulated error");

        // When
        Message result = jmsService.createErroredMessage(textMessage, rootCause);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getEmitterAlias()).isEqualTo("unknown");
        assertThat(result.getReceiverAlias()).isEqualTo("front");
        assertThat(result.getPayload()).isEqualTo("UNREADABLE_MESSAGE");
        assertThat(result.getDirection()).isEqualTo(Direction.INBOUND);
        assertThat(result.getStatus()).isEqualTo(MessageStatus.ERROR);
        assertThat(result.getMetadata())
                .asInstanceOf(InstanceOfAssertFactories.map(String.class, String.class))
                .containsEntry("jmsMessageID", "ID123")
                .containsEntry("timestamp", "1680000000000")
                .containsEntry("type", "Text")
                .containsEntry("destination", "queue://DEV.QUEUE.1")
                .containsEntry("error", "Simulated error");
    }


    @Test
    void shouldCreateErroredMessageFromTextMessageWithMissingReceiverAlias() throws Exception {
        // Given
        TextMessage textMessage = mock(TextMessage.class);
        when(textMessage.getStringProperty(EMITTER_ALIAS)).thenReturn("crm");
        when(textMessage.getStringProperty(RECEIVER_ALIAS)).thenThrow(new JMSException(RECEIVER_ALIAS + " missing"));
        when(textMessage.getJMSMessageID()).thenReturn("ID123");
        when(textMessage.getJMSTimestamp()).thenReturn(1680000000000L);
        when(textMessage.getJMSType()).thenReturn("Text");
        Destination destination = mock(Destination.class);
        when(destination.toString()).thenReturn("queue://DEV.QUEUE.1");
        when(textMessage.getJMSDestination()).thenReturn(destination);

        Exception rootCause = new Exception("Simulated error");

        // When
        Message result = jmsService.createErroredMessage(textMessage, rootCause);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getEmitterAlias()).isEqualTo("crm");
        assertThat(result.getReceiverAlias()).isEqualTo("unknown");
        assertThat(result.getPayload()).isEqualTo("UNREADABLE_MESSAGE");
        assertThat(result.getDirection()).isEqualTo(Direction.INBOUND);
        assertThat(result.getStatus()).isEqualTo(MessageStatus.ERROR);
        assertThat(result.getMetadata())
                .asInstanceOf(InstanceOfAssertFactories.map(String.class, String.class))
                .containsEntry("jmsMessageID", "ID123")
                .containsEntry("timestamp", "1680000000000")
                .containsEntry("type", "Text")
                .containsEntry("destination", "queue://DEV.QUEUE.1")
                .containsEntry("error", "Simulated error");
    }
}