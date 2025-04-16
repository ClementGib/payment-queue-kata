package com.cacib.pqk.persistence.partner;

import com.cacib.pqk.partner.application.ApplicationInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationInfoConverterTest {

    private ApplicationInfoConverter converter;

    @BeforeEach
    void setUp() {
        converter = new ApplicationInfoConverter();
    }

    private ApplicationInfo sample() {
        ApplicationInfo app = new ApplicationInfo();
        app.setName("crm-service");
        app.setTeam("DigitalFactory");
        app.setVersion("1.0.0");
        app.setUrl("https://crm.example.com");
        app.setContact("contact@crm.com");
        return app;
    }

    @Test
    void shouldConvertToJsonString() {
        ApplicationInfo appInfo = sample();

        String json = converter.convertToDatabaseColumn(appInfo);

        assertNotNull(json);
        assertTrue(json.contains("\"name\":\"crm-service\""));
        assertTrue(json.contains("\"team\":\"DigitalFactory\""));
    }

    @Test
    void shouldConvertToEntityAttribute() {
        String json = """
                {
                  "name": "crm-service",
                  "team": "DigitalFactory",
                  "version": "1.0.0",
                  "url": "https://crm.example.com",
                  "contact": "contact@crm.com"
                }
                """;

        ApplicationInfo app = converter.convertToEntityAttribute(json);

        assertNotNull(app);
        assertEquals("crm-service", app.getName());
        assertEquals("DigitalFactory", app.getTeam());
    }

    @Test
    void shouldReturnNullOnNullAttribute() {
        assertNull(converter.convertToDatabaseColumn(null));
        assertNull(converter.convertToEntityAttribute(null));
    }

    @Test
    void shouldThrowExceptionOnInvalidJson() {
        String invalidJson = "{ invalid json }";

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                converter.convertToEntityAttribute(invalidJson));

        assertTrue(exception.getMessage().contains("Could not deserialize ApplicationInfo"));
    }
}
