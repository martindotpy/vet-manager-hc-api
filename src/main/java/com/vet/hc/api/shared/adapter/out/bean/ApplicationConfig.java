package com.vet.hc.api.shared.adapter.out.bean;

import java.util.Properties;

import lombok.Getter;

@Getter
public class ApplicationConfig {
    private String securityJwtPassword;
    private Long securityJwtExpiration;

    public ApplicationConfig() {
        Properties properties = new Properties();

        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        securityJwtPassword = properties.getProperty("security.jwt.secret");
        securityJwtExpiration = Long.parseLong(properties.getProperty("security.jwt.expiration"));
    }
}
