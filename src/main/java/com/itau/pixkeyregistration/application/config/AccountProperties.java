package com.itau.pixkeyregistration.application.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app.account")
@Getter
public class AccountProperties {
    private int legalPersonLimitAccount;
    private int naturalPersonLimitAccount;
}
