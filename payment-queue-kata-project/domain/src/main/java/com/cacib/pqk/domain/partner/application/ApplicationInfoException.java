package com.cacib.pqk.domain.partner.application;

import com.cacib.pqk.domain.error.DomainException;

public class ApplicationInfoException extends DomainException {
    public ApplicationInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationInfoException(String errorMessage) {
        super(errorMessage);
    }
}
