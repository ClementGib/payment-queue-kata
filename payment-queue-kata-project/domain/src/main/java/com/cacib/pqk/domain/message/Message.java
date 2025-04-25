package com.cacib.pqk.domain.message;

import com.cacib.pqk.domain.partner.type.EnumValidator;
import com.cacib.pqk.domain.partner.type.Direction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@Generated
public class Message {

    private Long id;

    @NotBlank(message = "Emitter alias is required and should have at least 1 character")
    @Size(max = 50, message = "Emitter alias must be at most 50 characters")
    private String emitterAlias;

    @NotBlank(message = "Receiver alias is required and should have at least 1 character")
    @Size(max = 50, message = "Receiver alias must be at most 50 characters")
    private String receiverAlias;

    @NotNull
    private String payload;

    @NotNull
    private LocalDateTime receivedAt;

    @EnumValidator(enumClass = Direction.class, message = "Direction must be INBOUND or OUTBOUND")
    private Direction direction;

    @NotNull
    private Map<String, String> metadata;

    @EnumValidator(enumClass = MessageStatus.class, message = "MessageStatus must be a valid status")
    private MessageStatus status;
}
