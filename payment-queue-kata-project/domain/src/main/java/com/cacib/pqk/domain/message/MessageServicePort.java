package com.cacib.pqk.domain.message;

import com.cacib.pqk.domain.error.InboundException;
import com.cacib.pqk.domain.error.OutboundException;

import java.util.Optional;
import java.util.Set;

public interface MessageServicePort {
    /**
     * find all Messages
     *
     * @return Set with all Message
     */
    Set<Message> getAll();

    /**
     * process inbound Message
     *
     * @param inboundMessage to process
     * @return
     */
    Optional<Message> processInboundMessage(Message inboundMessage) throws InboundException;

    /**
     * process outbound Message
     *
     * @param outboundMessage to process and send
     */
    Optional<Message> processOutboundMessage(Message outboundMessage) throws OutboundException;

    /**
     * process new Message
     *
     * @param newErroredMessage to process
     */
    void processWithError(Message newErroredMessage);
}
