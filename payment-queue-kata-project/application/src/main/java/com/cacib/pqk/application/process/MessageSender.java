package com.cacib.pqk.application.process;

import com.cacib.pqk.message.Message;

public interface MessageSender {
    void sendMessage(Message message);
}
