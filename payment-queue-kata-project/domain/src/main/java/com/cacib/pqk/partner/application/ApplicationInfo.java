package com.cacib.pqk.partner.application;

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
}
