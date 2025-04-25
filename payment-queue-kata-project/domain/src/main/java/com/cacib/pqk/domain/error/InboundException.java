package com.cacib.pqk.domain.error;

import lombok.Generated;

public class InboundException extends DomainException {

    @Generated
    public InboundException(String errorMessage) {
        super(errorMessage);
    }

    @Generated
    public InboundException(String message, Throwable cause) {
        super(message, cause);
    }
}
