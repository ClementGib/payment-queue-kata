package com.cacib.pqk.application.message;

import com.cacib.pqk.application.broker.MessageSender;
import com.cacib.pqk.domain.error.OutboundException;
import com.cacib.pqk.domain.message.Message;
import com.cacib.pqk.domain.message.MessagePersistencePort;
import com.cacib.pqk.domain.message.MessageStatus;
import com.cacib.pqk.domain.partner.Partner;
import com.cacib.pqk.domain.partner.PartnerPersistencePort;
import com.cacib.pqk.domain.partner.type.Direction;
import jakarta.jms.JMSException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension .class)
class MessageServiceImplTest {

    @Mock
    MessagePersistencePort messagePersistencePort;

    @Mock
    PartnerPersistencePort partnerPersistencePort;

    @Mock
    MessageSender messageSender;

    @InjectMocks
    MessageServiceImpl messageService;

    @Test
    void shouldGetAllMessages() {
        Message message1 = Message.builder()
                .id(1L)
                .receiverAlias("1")
                .emitterAlias("2")
                .payload("{}")
                .receivedAt(LocalDateTime.of(2025, 12, 10, 4, 30))
                .direction(Direction.INBOUND)
                .metadata(Map.of("key1", "value1"))
                .status(MessageStatus.IN_PROGRESS)
                .build();
        Message message2 = Message.builder()
                .id(2L)
                .receiverAlias("2")
                .emitterAlias("1")
                .payload("{}")
                .receivedAt(LocalDateTime.of(2025, 10, 12, 4, 30))
                .direction(Direction.OUTBOUND)
                .metadata(Map.of("key2", "value2"))
                .status(MessageStatus.ERROR)
                .build();
        Set<Message> messages = Set.of(message1, message2);

        when(messagePersistencePort.getAll()).thenReturn(messages);
        Set<Message> actualMessages = messageService.getAll();
        actualMessages.containsAll(messages);
    }

    @Test
    void shouldProcessAndSendMessageIfReceiverExists() throws JMSException {
        // Given
        Message outboundMessage = Message.builder()
                .receiverAlias("crm")
                .payload("{\"event\": \"new\"}")
                .metadata(new HashMap<>())
                .status(MessageStatus.RECEIVED)
                .build();

        Partner receiver = new Partner();
        receiver.setAlias("crm");

        when(partnerPersistencePort.getByAlias("crm")).thenReturn(Optional.of(receiver));
        when(messagePersistencePort.create(any())).thenReturn(Optional.of(outboundMessage));

        // When
        Optional<Message> result = messageService.processOutboundMessage(outboundMessage);

        // Then
        verify(messageSender).sendMessage(eq(outboundMessage), eq(receiver));
        assertThat(result).isPresent();
        assertThat(result.get().getStatus()).isEqualTo(MessageStatus.PROCESSED);
    }

    @Test
    void shouldReturnErrorWhenReceiverNotFound() throws JMSException {
        // Given
        Message outboundMessage = Message.builder()
                .receiverAlias("unknown")
                .payload("{}")
                .metadata(new HashMap<>())
                .status(MessageStatus.RECEIVED)
                .build();

        when(partnerPersistencePort.getByAlias("unknown")).thenReturn(Optional.empty());
        when(messagePersistencePort.create(any())).thenAnswer(inv -> Optional.of(inv.getArgument(0)));

        // When
        Optional<Message> result = messageService.processOutboundMessage(outboundMessage);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getStatus()).isEqualTo(MessageStatus.ERROR);
        assertThat(result.get().getMetadata())
                .containsEntry("ERROR:", "Missing receiver partner: unknown");
        verify(messageSender, never()).sendMessage(any(), any());
    }

    @Test
    void shouldThrowOutboundExceptionIfDbFails() {
        // Given
        Message outboundMessage = Message.builder()
                .receiverAlias("crm")
                .payload("{}")
                .metadata(new HashMap<>())
                .status(MessageStatus.RECEIVED)
                .build();

        when(messagePersistencePort.create(any())).thenThrow(new DataAccessException("DB is down") {});

        // When / Then
        assertThatThrownBy(() -> messageService.processOutboundMessage(outboundMessage))
                .isInstanceOf(OutboundException.class)
                .hasMessageContaining("DB is down");
    }

    @Test
    void shouldSetUnreachableStatusOnJmsError() throws JMSException {
        // Given
        Message outboundMessage = Message.builder()
                .receiverAlias("crm")
                .payload("{}")
                .metadata(new HashMap<>())
                .status(MessageStatus.RECEIVED)
                .build();

        Partner receiver = new Partner();
        receiver.setAlias("crm");

        when(partnerPersistencePort.getByAlias("crm")).thenReturn(Optional.of(receiver));
        doThrow(new JMSException("JMS failure"))
                .when(messageSender).sendMessage(any(), any());

        // When / Then
        assertThatThrownBy(() -> messageService.processOutboundMessage(outboundMessage))
                .isInstanceOf(OutboundException.class)
                .hasMessageContaining("JMS failure");

        verify(messagePersistencePort, atLeast(2)).create(any());
        assertThat(outboundMessage.getStatus()).isEqualTo(MessageStatus.UNREACHABLE);
    }
}