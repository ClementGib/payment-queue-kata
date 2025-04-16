package com.cacib.pqk.partner;

import com.cacib.pqk.error.DomainException;
import lombok.Generated;

@Generated
public class PartnerException extends DomainException {
    public PartnerException(String errorMessage) {
        super(errorMessage);
    }

    public PartnerException(String message, Throwable cause) {
        super(message, cause);
    }
}
