package com.cacib.pqk.domain.partner;

import com.cacib.pqk.domain.error.DomainException;
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
