package com.cacib.pqk.domain.message;

import java.util.Optional;
import java.util.Set;

public interface MessageControllerPort {


    /**
     * Find all Message
     *
     * @return all Message found
     */
    Set<Message> getAll();

    /**
     * Create a new Message
     *
     * @param newMessage to create
     * @return Optional Message if created or not
     */
    Optional<Message> create(Message newMessage);
}
