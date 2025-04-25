package com.cacib.pqk.client.serialization;

import com.cacib.pqk.domain.partner.type.Direction;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class DirectionDeserializer extends JsonDeserializer<Direction> {

    @Override
    public Direction deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getText().toUpperCase();
        try {
            return Direction.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid direction: " + value);
        }
    }
}
