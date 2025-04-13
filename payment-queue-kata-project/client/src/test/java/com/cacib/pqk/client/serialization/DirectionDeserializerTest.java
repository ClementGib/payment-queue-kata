package com.cacib.pqk.client.serialization;

import com.cacib.pqk.partner.type.Direction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionDeserializerTest {

    private ObjectMapper objectMapper;

    static class Wrapper {
        public Direction direction;
    }

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Direction.class, new DirectionDeserializer());
        objectMapper.registerModule(module);
    }

    @Test
    void shouldDeserializeInboundDirection() throws JsonProcessingException {
        String json = "{\"direction\": \"INBOUND\"}";

        Wrapper wrapper = objectMapper.readValue(json, Wrapper.class);

        assertEquals(Direction.INBOUND, wrapper.direction);
    }

    @Test
    void shouldDeserializeOutboundDirection() throws JsonProcessingException {
        String json = "{\"direction\": \"OUTBOUND\"}";

        Wrapper wrapper = objectMapper.readValue(json, Wrapper.class);

        assertEquals(Direction.OUTBOUND, wrapper.direction);
    }

    @Test
    void shouldThrowExceptionForInvalidDirection() {
        String json = "{\"direction\": \"INVALID\"}";

        JsonMappingException exception = assertThrows(JsonMappingException.class, () -> {
            objectMapper.readValue(json, Wrapper.class);
        });

        Throwable cause = exception.getCause();
        assertTrue(cause instanceof IllegalArgumentException);
        assertTrue(cause.getMessage().contains("Invalid direction"));
    }
}