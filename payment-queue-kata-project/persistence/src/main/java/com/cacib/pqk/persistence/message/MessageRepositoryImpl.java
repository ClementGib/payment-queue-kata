package com.cacib.pqk.persistence.message;

import com.cacib.pqk.domain.message.Message;
import com.cacib.pqk.domain.message.MessagePersistencePort;
import com.cacib.pqk.persistence.repository.MessageJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class MessageRepositoryImpl implements MessagePersistencePort {

    private final MessageJpaRepository messageJpaRepository;
    private final MessageMapper messageMapper;

    @Autowired
    public MessageRepositoryImpl(MessageJpaRepository messageJpaRepository, MessageMapper messageMapper) {
        this.messageJpaRepository = messageJpaRepository;
        this.messageMapper = messageMapper;
    }

    @Override
    public Set<Message> getAll() {
        return messageJpaRepository.findAll().stream()
                .map(messageMapper::toDomain)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<Message> create(Message message) {
        MessageEntity entity = messageMapper.toEntity(message);
        MessageEntity save = messageJpaRepository.save(entity);
        return Optional.of(messageMapper.toDomain(save));
    }
}