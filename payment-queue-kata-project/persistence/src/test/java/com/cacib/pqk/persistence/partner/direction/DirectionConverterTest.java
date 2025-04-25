package com.cacib.pqk.persistence.partner.direction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.cacib.pqk.domain.partner.type.Direction.*;
import static org.junit.jupiter.api.Assertions.*;

class DirectionConverterTest {

    private DirectionConverter converter;

    @BeforeEach
    void setUp() {
        converter = new DirectionConverter();
    }

    @Test
    void shouldConvertInboundToChar() {
        assertEquals(STR_INBOUND, converter.convertToDatabaseColumn(INBOUND));
    }

    @Test
    void shouldConvertOutboundToChar() {
        assertEquals(STR_OUTBOUND, converter.convertToDatabaseColumn(OUTBOUND));
    }

    @Test
    void shouldConvertCharToInbound() {
        assertEquals(INBOUND, converter.convertToEntityAttribute(STR_INBOUND));
    }

    @Test
    void shouldConvertCharToOutbound() {
        assertEquals(OUTBOUND, converter.convertToEntityAttribute(STR_OUTBOUND));
    }

    @Test
    void shouldReturnNullForNullDirection() {
        assertNull(converter.convertToDatabaseColumn(null));
    }

    @Test
    void shouldReturnNullForNullChar() {
        assertNull(converter.convertToEntityAttribute(null));
    }

    @Test
    void shouldThrowExceptionForInvalidChar() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                converter.convertToEntityAttribute('X'));

        assertEquals("Unexpected direction value: X", exception.getMessage());
    }
}
