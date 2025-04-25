package com.cacib.pqk.application.broker;

import com.cacib.pqk.application.jms.JmsService;
import com.cacib.pqk.domain.message.Message;
import com.cacib.pqk.domain.message.MessageServicePort;
import jakarta.jms.JMSRuntimeException;
import jakarta.jms.TextMessage;
import org.apache.activemq.artemis.commons.shaded.json.JsonException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MqMessageListenerTest {

    private JmsService jmsService;
    private MessageServicePort messageService;
    private MqMessageListener listener;

    @BeforeEach
    void setUp() {
        jmsService = mock(JmsService.class);
        messageService = mock(MessageServicePort.class);
        listener = new MqMessageListener(jmsService, messageService);
    }

    @Test
    void shouldProcessMessageSuccessfully() throws Exception {
        // Given
        TextMessage textMessage = mock(TextMessage.class);
        Message expectedMessage = mock(Message.class);

        when(jmsService.extractMessage(textMessage)).thenReturn(expectedMessage);

        // When
        listener.onMessage(textMessage);

        // Then
        verify(messageService).processInboundMessage(expectedMessage);
        verifyNoMoreInteractions(messageService);
    }

    @Test
    void shouldProcessErroredMessageAndThrowRuntimeException() throws Exception {
        // Given
        TextMessage textMessage = mock(TextMessage.class);
        JsonException exception = new JsonException("Invalid format");
        Message errored = mock(Message.class);

        when(jmsService.extractMessage(textMessage)).thenThrow(exception);
        when(jmsService.createErroredMessage(textMessage, exception)).thenReturn(errored);

        // When / Then
        try {
            listener.onMessage(textMessage);
        } catch (JMSRuntimeException e) {
            verify(messageService).processWithError(errored);
        }
    }

    @Test
    void shouldThrowIllegalArgumentForUnsupportedMessage() {
        jakarta.jms.Message otherMessage = mock(jakarta.jms.Message.class);

        assertThrows(IllegalArgumentException.class, () -> listener.onMessage(otherMessage));
    }
}