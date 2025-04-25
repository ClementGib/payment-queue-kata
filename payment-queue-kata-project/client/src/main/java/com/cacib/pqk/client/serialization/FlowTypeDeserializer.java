package com.cacib.pqk.client.serialization;

import com.cacib.pqk.domain.partner.type.FlowType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class FlowTypeDeserializer extends JsonDeserializer<FlowType> {

    @Override
    public FlowType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getText().toUpperCase();
        try {
            return FlowType.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid direction: " + value);
        }
    }
}
