package com.cacib.pqk.application.partner.flow;

import com.cacib.pqk.partner.type.FlowType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.cacib.pqk.partner.type.FlowType.*;
import static org.junit.jupiter.api.Assertions.*;

class FlowTypeConverterTest {

    private FlowTypeConverter converter;

    @BeforeEach
    void setUp() {
        converter = new FlowTypeConverter();
    }

    @Test
    void shouldConvertMessageToChar() {
        assertEquals(STR_MESSAGE, converter.convertToDatabaseColumn(MESSAGE));
    }

    @Test
    void shouldConvertAlertingToChar() {
        assertEquals(STR_ALERTING, converter.convertToDatabaseColumn(ALERTING));
    }

    @Test
    void shouldConvertNotificationToChar() {
        assertEquals(STR_NOTIFICATION, converter.convertToDatabaseColumn(NOTIFICATION));
    }

    @Test
    void shouldConvertCharToMessage() {
        assertEquals(MESSAGE, converter.convertToEntityAttribute(STR_MESSAGE));
    }

    @Test
    void shouldConvertCharToAlerting() {
        assertEquals(ALERTING, converter.convertToEntityAttribute(STR_ALERTING));
    }

    @Test
    void shouldConvertCharToNotification() {
        assertEquals(NOTIFICATION, converter.convertToEntityAttribute(STR_NOTIFICATION));
    }

    @Test
    void shouldReturnNullWhenFlowTypeIsNull() {
        assertNull(converter.convertToDatabaseColumn(null));
    }

    @Test
    void shouldReturnNullWhenCharIsNull() {
        assertNull(converter.convertToEntityAttribute(null));
    }

    @Test
    void shouldThrowWhenInvalidChar() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                converter.convertToEntityAttribute('X')
        );

        assertEquals("Unexpected flow type value: X", exception.getMessage());
    }
}
