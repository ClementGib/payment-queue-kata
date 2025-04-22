package com.cacib.pqk.message;

import java.util.Set;

public interface MessageServicePort {
    /**
     * find all Messages
     *
     * @return Set with all Message
     */
    Set<Message> getAll();

    /**
     * process new Message
     *
     * @param newMessage to process
     */
    void process(Message newMessage);


    /**
     * process new Message
     *
     * @param newErroredMessage to process
     */
    void processWithError(Message newErroredMessage);
}
