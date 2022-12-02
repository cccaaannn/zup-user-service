package com.kurtcan.zupuserservice.config;

import com.kurtcan.javacore.security.encryption.abstracts.IPasswordEncryptor;
import com.kurtcan.javacore.security.encryption.jasypt.JasyptPasswordEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptionConfig {

    @Bean
    IPasswordEncryptor getPasswordEncryptor() {
        return new JasyptPasswordEncryptor();
    }

}
