package com.cacib.pqk.domain.partner.application;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Generated;


@Data
@Generated
public class ApplicationInfo {

    @NotBlank
    private String name;

    @NotBlank
    private String team;

    @NotBlank
    private String version;

    @NotBlank
    private String url;

    @NotBlank
    private String contact;

    @NotBlank
    private String channel;

    @NotBlank
    private String queueManager;

    @NotBlank
    private String host;

    private int port;

    @NotBlank
    private String queue;

    @NotBlank
    private String user;

    @NotBlank
    private String password;
}
