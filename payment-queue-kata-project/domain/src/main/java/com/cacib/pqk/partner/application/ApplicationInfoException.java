package com.cacib.pqk.partner.application;

import com.cacib.pqk.error.DomainException;

public class ApplicationInfoException extends DomainException {
    public ApplicationInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationInfoException(String errorMessage) {
        super(errorMessage);
    }
}
