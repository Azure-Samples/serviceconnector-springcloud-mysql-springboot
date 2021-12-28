/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.example.demo;

import javax.sql.DataSource;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenCredential;
import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.ManagedIdentityCredentialBuilder;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource dataSource() throws Exception {
        String url = getUrl();
        String username = getUserName();
        String accessToken = getPassword();

        return DataSourceBuilder.create()
                                .driverClassName("com.mysql.jdbc.Driver")
                                .url(url)
                                .username(username)
                                .password(accessToken)
                                .build();
    }
    
    private String getUrl() {
        return System.getenv("AZURE_MYSQL_URL");
    }

    private String getUserName() {
        return System.getenv("AZURE_MYSQL_USERNAME");
    }
    
    private String getPassword() {
        TokenCredential defaultCredential = new ManagedIdentityCredentialBuilder().build();
        AccessToken token = defaultCredential.getToken(new TokenRequestContext().addScopes("https://ossrdbms-aad.database.windows.net/.default")).block();
        return token.getToken();
    }

}