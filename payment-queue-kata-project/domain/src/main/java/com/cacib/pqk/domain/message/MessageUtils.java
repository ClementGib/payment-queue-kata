package com.cacib.pqk.domain.message;

import com.cacib.pqk.domain.partner.type.Direction;

public class MessageUtils {

    private MessageUtils() {}

    public static Message toOutboundMessage(Message intboundMessage) {
        return Message.builder()
                .emitterAlias(intboundMessage.getEmitterAlias())
                .receivedAt(intboundMessage.getReceivedAt())
                .payload(intboundMessage.getPayload())
                .direction(Direction.OUTBOUND)
                .metadata(intboundMessage.getMetadata())
                .status(MessageStatus.IN_PROGRESS)
                .build();
    }
}