package com.cdx.bas.bootstrap;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.cacib.pqk")
public class BootstrapApplication {
    public static void main(String[] args) {
        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(BootstrapApplication.class)
                .properties("spring.config.additional-location=classpath:/application.yml")
                .profiles("persistence");
        springApplicationBuilder.run(args);
    }
}
