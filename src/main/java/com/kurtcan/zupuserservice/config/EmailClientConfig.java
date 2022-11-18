package com.kurtcan.zupuserservice.config;

import com.kurtcan.zupuserservice.core.utilities.email.abstracts.IEmailClient;
import com.kurtcan.zupuserservice.core.utilities.email.javax.gmail.JavaxGmailClient;
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
        return new JavaxGmailClient(username, password);
    }

}
