package com.cacib.pqk.persistence.partner.flow;

import com.cacib.pqk.domain.partner.type.FlowType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import static com.cacib.pqk.domain.partner.type.FlowType.*;

@Converter
public class FlowTypeConverter implements AttributeConverter<FlowType, Character> {

    static final String ERROR_FLOW_TYPE = "Unexpected flow type value: ";

    @Override
    public Character convertToDatabaseColumn(FlowType flowType) {
        if (flowType == null) {
            return null;  // Handle null flowType by returning null
        }
        return switch (flowType) {
            case MESSAGE -> STR_MESSAGE;
            case ALERTING -> STR_ALERTING;
            case NOTIFICATION -> STR_NOTIFICATION;
        };
    }

    @Override
    public FlowType convertToEntityAttribute(Character flowTypeCode) {
        if (flowTypeCode == null) {
            return null;  // Handle null flowTypeCode by returning null
        }
        return switch (flowTypeCode) {
            case STR_MESSAGE -> MESSAGE;
            case STR_ALERTING -> ALERTING;
            case STR_NOTIFICATION -> NOTIFICATION;
            default -> throw new IllegalStateException(ERROR_FLOW_TYPE + flowTypeCode);
        };
    }
}
