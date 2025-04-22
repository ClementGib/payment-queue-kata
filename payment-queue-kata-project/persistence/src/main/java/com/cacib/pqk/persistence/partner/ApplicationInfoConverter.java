package com.cacib.pqk.persistence.partner;

import com.cacib.pqk.partner.application.ApplicationInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

public class ApplicationInfoConverter implements AttributeConverter<ApplicationInfo, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ApplicationInfo attribute) {
        try {
            return attribute != null ? objectMapper.writeValueAsString(attribute) : null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not serialize ApplicationInfo", e);
        }
    }

    @Override
    public ApplicationInfo convertToEntityAttribute(String dbData) {
        try {
            return dbData != null ? objectMapper.readValue(dbData, ApplicationInfo.class) : null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not deserialize ApplicationInfo", e);
        }
    }
}
