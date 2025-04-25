package com.cacib.pqk.persistence.message;

import com.cacib.pqk.domain.message.MessageStatus;
import com.cacib.pqk.domain.partner.type.Direction;
import com.cacib.pqk.persistence.partner.direction.DirectionConverter;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;

@Entity
@Table(schema = "pqkapp", name = "messages", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@Getter
@Setter
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emitter_alias", nullable = false, length = 50)
    private String emitterAlias;

    @Column(name = "receiver_alias", nullable = false, length = 50)
    private String receiverAlias;

    @Type(JsonType.class)
    @Column(name = "payload", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    private String payload;

    @Column(name = "received_at", nullable = false)
    private LocalDateTime receivedAt = LocalDateTime.now();

    @Column(name = "direction", nullable = false, length = 1)
    @Convert(converter = DirectionConverter.class)
    private Direction direction;

    @Type(JsonType.class)
    @Column(name = "metadata", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    private String metadata;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private MessageStatus status;
}
