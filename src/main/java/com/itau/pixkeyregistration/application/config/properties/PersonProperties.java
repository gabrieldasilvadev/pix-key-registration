package com.itau.pixkeyregistration.application.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("app.person")
@Getter
@Setter
public class PersonProperties {
    private int legalPersonLimitAccount;
    private int naturalPersonLimitAccount;
    private int pixKeysLegalPersonLimit;
    private int pixKeysNaturalPersonLimit;
}
