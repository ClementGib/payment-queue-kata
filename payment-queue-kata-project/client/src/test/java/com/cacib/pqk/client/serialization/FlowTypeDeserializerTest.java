package com.cacib.pqk.client.serialization;

import com.cacib.pqk.partner.type.FlowType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlowTypeDeserializerTest {

    private ObjectMapper objectMapper;

    static class Wrapper {
        public FlowType flowType;
    }

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(FlowType.class, new FlowTypeDeserializer());
        objectMapper.registerModule(module);
    }

    @Test
    void shouldDeserializeMessageFlowType() throws JsonProcessingException {
        String json = "{\"flowType\": \"MESSAGE\"}";

        Wrapper wrapper = objectMapper.readValue(json, Wrapper.class);

        assertEquals(FlowType.MESSAGE, wrapper.flowType);
    }

    @Test
    void shouldDeserializeAlertingFlowType() throws JsonProcessingException {
        String json = "{\"flowType\": \"ALERTING\"}";

        Wrapper wrapper = objectMapper.readValue(json, Wrapper.class);

        assertEquals(FlowType.ALERTING, wrapper.flowType);
    }

    @Test
    void shouldThrowExceptionForInvalidFlowType() {
        String json = "{\"flowType\": \"INVALID\"}";

        JsonMappingException exception = assertThrows(JsonMappingException.class, () -> {
            objectMapper.readValue(json, Wrapper.class);
        });

        Throwable rootCause = exception.getCause();
        assertNotNull(rootCause);
        assertInstanceOf(IllegalArgumentException.class, rootCause);
        assertTrue(rootCause.getMessage().contains("Invalid direction"));
    }
}