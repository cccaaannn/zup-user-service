package com.can.zupuserservice.config;

import com.can.zupuserservice.core.utilities.email.abstracts.IEmailClient;
import com.can.zupuserservice.core.utilities.email.concretes.GmailClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailClientConfig {

    @Value("${email.gmail.username}")
    String username;

    @Value("${email.gmail.password}")
    String password;

    @Bean
    public IEmailClient getEmailClient() {
        return new GmailClient(username, password);
    }

}
