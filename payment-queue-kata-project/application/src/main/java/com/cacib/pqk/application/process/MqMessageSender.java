package com.cacib.pqk.application.process;

import com.cacib.pqk.message.Message;
import org.springframework.stereotype.Component;

@Component
public class MqMessageSender implements MessageSender{

public void sendMessage(Message message) {
    //TODO send back the message to expected receiver
}
}
