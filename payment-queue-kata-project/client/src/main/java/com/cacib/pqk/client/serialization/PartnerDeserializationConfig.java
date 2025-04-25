package com.cacib.pqk.client.serialization;

import com.cacib.pqk.domain.partner.type.Direction;
import com.cacib.pqk.domain.partner.type.FlowType;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PartnerDeserializationConfig {

    @Bean
    public SimpleModule partnerModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Direction.class, new DirectionDeserializer());
        module.addDeserializer(FlowType.class, new FlowTypeDeserializer());
        return module;
    }
}
