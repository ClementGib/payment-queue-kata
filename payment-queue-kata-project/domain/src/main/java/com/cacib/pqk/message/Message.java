package com.cacib.pqk.message;

import com.cacib.pqk.partner.type.Direction;
import com.cacib.pqk.partner.type.EnumValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;

import java.time.LocalDateTime;

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
    private String metadata;

    @EnumValidator(enumClass = MessageStatus.class, message = "MessageStatus must be a valid status")
    private MessageStatus status;
}
