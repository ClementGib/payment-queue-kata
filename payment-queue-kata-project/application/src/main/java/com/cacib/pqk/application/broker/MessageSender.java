package com.cacib.pqk.application.broker;

import com.cacib.pqk.domain.error.OutboundException;
import com.cacib.pqk.domain.message.Message;
import com.cacib.pqk.domain.partner.Partner;
import jakarta.jms.JMSException;

public interface MessageSender {
    void sendMessage(Message message, Partner receiverPartner) throws OutboundException, JMSException;
}
