package com.cacib.pqk.persistence.partner.json;

import com.cacib.pqk.partner.application.ApplicationInfo;
import com.cacib.pqk.partner.application.ApplicationInfoException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonConverter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ApplicationInfo fromJson(String json) {
        try {
            return objectMapper.readValue(json, ApplicationInfo.class);
        } catch (JsonProcessingException e) {
            throw new ApplicationInfoException("Failed to convert JSON to ApplicationInfo", e);
        }
    }

    public String toJson(ApplicationInfo appInfo) {
        try {
            return objectMapper.writeValueAsString(appInfo);
        } catch (JsonProcessingException e) {
            throw new ApplicationInfoException("Failed to convert ApplicationInfo to JSON", e);
        }
    }
}
