package com.cacib.pqk.message;

import java.util.Optional;
import java.util.Set;

public interface MessagePersistencePort {

    /**
     * find every Messages
     * @return all Messages
     */
    public Set<Message> getAll();
    
    /**
     * create the current Message
     *
     * @param message to create
     * @return created Message
     */
    public Optional<Message> create(Message message);
}
