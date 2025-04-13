package com.cacib.pqk.application.partner.direction;

import com.cacib.pqk.partner.type.Direction;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import static com.cacib.pqk.partner.type.Direction.*;

@Converter
public class DirectionConverter implements AttributeConverter<Direction, Character> {

    static final String ERROR_DIRECTION = "Unexpected direction value: ";

    @Override
    public Character convertToDatabaseColumn(Direction direction) {
        if (direction == null) {
            return null;  // Handle null Direction by returning null
        }
        return switch (direction) {
            case INBOUND -> STR_INBOUND;
            case OUTBOUND -> STR_OUTBOUND;
        };
    }

    @Override
    public Direction convertToEntityAttribute(Character directionCode) {
        if (directionCode == null) {
            return null;  // Handle null directionCode by returning null
        }
        return switch (directionCode) {
            case STR_INBOUND -> INBOUND;
            case STR_OUTBOUND -> OUTBOUND;
            default -> throw new IllegalStateException(ERROR_DIRECTION + directionCode);
        };
    }
}
