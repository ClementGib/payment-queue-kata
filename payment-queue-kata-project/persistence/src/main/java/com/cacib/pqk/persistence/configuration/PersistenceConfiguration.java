package com.cacib.pqk.persistence.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.cacib.pqk.persistence")
@EnableJpaRepositories(basePackages = "com.cacib.pqk.persistence.repository")
public class PersistenceConfiguration {
}

