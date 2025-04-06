package com.cdx.bas.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.cacib.pqk.client", "com.cacib.pqk.application"})
public class BootstrapApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootstrapApplication.class, args);
    }
}
