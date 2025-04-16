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

    @NotBlank(message = "Alias is required")
    @Size(max = 50, message = "Alias must be at most 50 characters")
    private String partnerAlias;

    @NotNull
    private String payload;

    @NotNull
    private LocalDateTime receivedAt = LocalDateTime.now();

    @EnumValidator(enumClass = Direction.class, message = "Direction must be INBOUND or OUTBOUND")
    private Direction direction;

    @NotNull
    private String metadata;

    @EnumValidator(enumClass = MessageStatus.class, message = "MessageStatus must be a valid status")
    private MessageStatus status;
}
