package com.cacib.pqk.client.message;

import com.cacib.pqk.message.Message;
import com.cacib.pqk.message.MessageControllerPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/messages")
public class MessageController implements MessageControllerPort {

    @Override
    public Set<Message> getAll() {
        return Set.of();
    }

    @GetMapping("{id}")
    @Override
    public Optional<Message> create(Message newMessage) {
        return Optional.empty();
    }
//    GET /messages → pour afficher dans un tableau
//
//    GET /messages/{id} → pour afficher dans une popin le détail du message
}
