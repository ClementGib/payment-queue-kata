package com.cacib.pqk.application.message;

import com.cacib.pqk.message.Message;
import com.cacib.pqk.message.MessagePersistencePort;
import com.cacib.pqk.message.MessageServicePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MessageServiceImpl implements MessageServicePort {

    @Autowired
    public MessageServiceImpl(MessagePersistencePort messagePersistencePort) {
        this.messagePersistencePort = messagePersistencePort;
    }

    MessagePersistencePort messagePersistencePort;

    Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
    @Override
    public Set<Message> getAll() {
        return Set.of();
    }

    @Override
    public void process(Message newMessage) {
        logger.info("Message received: " + newMessage);
        messagePersistencePort.create(newMessage);
    }

    @Override
    public void processWithError(Message newErroredMessage) {
        logger.info("Message received: " + newErroredMessage);
        messagePersistencePort.create(newErroredMessage);
    }
}
