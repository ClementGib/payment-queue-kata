package com.cdx.bas.client;

import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BankAccountController {

    @GetMapping("/hello")
    public String helloBankAccount() {
        return "Hello Bank Account!";
    }
}


