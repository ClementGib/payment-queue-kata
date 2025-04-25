package com.cacib.pqk.application.message;

import com.cacib.pqk.application.broker.MessageSender;
import com.cacib.pqk.domain.error.InboundException;
import com.cacib.pqk.domain.error.OutboundException;
import com.cacib.pqk.domain.message.Message;
import com.cacib.pqk.domain.message.MessagePersistencePort;
import com.cacib.pqk.domain.message.MessageServicePort;
import com.cacib.pqk.domain.message.MessageStatus;
import com.cacib.pqk.domain.partner.Partner;
import com.cacib.pqk.domain.partner.PartnerPersistencePort;
import jakarta.jms.JMSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class MessageServiceImpl implements MessageServicePort {

    Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    public static final String MESSAGE_RECEIVED_FORMAT = "Message received from: %s";
    public static final String MESSAGE_TO_SEND_FORMAT = "Message to send to: %s";
    public static final String ERRORED_MESSAGE_FORMAT = "Errored message: %s";
    public static final String ERROR_FIELD = "ERROR:";
    public static final String MESSAGE_MISSING_PARTNER = "Missing receiver partner: %s";

    @Autowired
    public MessageServiceImpl(MessagePersistencePort messagePersistencePort,
                              PartnerPersistencePort partnerPersistencePort,
                              MessageSender messageSender) {
        this.messagePersistencePort = messagePersistencePort;
        this.partnerPersistencePort = partnerPersistencePort;
        this.messageSender = messageSender;
    }

    MessagePersistencePort messagePersistencePort;
    PartnerPersistencePort partnerPersistencePort;
    MessageSender messageSender;

    @Override
    public Set<Message> getAll() {
        return messagePersistencePort.getAll();
    }

    @Override
    public Optional<Message> processInboundMessage(Message inboundMessage) throws InboundException {
        logger.info(String.format(MESSAGE_RECEIVED_FORMAT, inboundMessage.getEmitterAlias()));
        inboundMessage.setStatus(MessageStatus.IN_PROGRESS);
        try {
            return messagePersistencePort.create(inboundMessage);
        } catch (DataAccessException exception) {
            throw new InboundException(exception.getMessage());
        }
    }

    @Override
    public Optional<Message> processOutboundMessage(Message outboundMessage) throws OutboundException {
        logger.info(String.format(MESSAGE_TO_SEND_FORMAT, outboundMessage.getReceiverAlias()));
        try {
            messagePersistencePort.create(outboundMessage);
            Optional<Partner> receiverPartner = partnerPersistencePort.getByAlias(outboundMessage.getReceiverAlias());
            if (receiverPartner.isPresent()) {
                outboundMessage.setStatus(MessageStatus.PROCESSED);
                messageSender.sendMessage(outboundMessage, receiverPartner.get());
                return messagePersistencePort.create(outboundMessage);
            } else {
                String missingReceiverError = String.format(MESSAGE_MISSING_PARTNER, outboundMessage.getReceiverAlias());
                logger.error(missingReceiverError);
                outboundMessage.setStatus(MessageStatus.ERROR);
                outboundMessage.getMetadata().put(ERROR_FIELD, missingReceiverError);
                return messagePersistencePort.create(outboundMessage);
            }
        } catch (DataAccessException exception) {
            throw new OutboundException(exception.getMessage());
        } catch (JMSException exception) {
            outboundMessage.setStatus(MessageStatus.UNREACHABLE);
            messagePersistencePort.create(outboundMessage);
            throw new OutboundException(exception.getMessage());
        }
    }

    @Override
    public void processWithError(Message newErroredMessage) {
        logger.error(String.format(ERRORED_MESSAGE_FORMAT, newErroredMessage));
        messagePersistencePort.create(newErroredMessage);
    }
}
