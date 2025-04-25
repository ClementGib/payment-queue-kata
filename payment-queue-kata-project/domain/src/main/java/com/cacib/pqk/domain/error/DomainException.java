package com.cacib.pqk.domain.error;


import lombok.Generated;

public class DomainException extends RuntimeException {

    @Generated
    public DomainException(String errorMessage) {
        super(errorMessage);
    }

    @Generated
    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }

}
