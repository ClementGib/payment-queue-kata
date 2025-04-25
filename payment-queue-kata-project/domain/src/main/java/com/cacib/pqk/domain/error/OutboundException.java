package com.cacib.pqk.domain.error;

import lombok.Generated;

public class OutboundException extends DomainException {

    @Generated
    public OutboundException(String errorMessage) {
        super(errorMessage);
    }

    @Generated
    public OutboundException(String message, Throwable cause) {
        super(message, cause);
    }
}
