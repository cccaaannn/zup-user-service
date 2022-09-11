package com.can.zupuserservice.config;

import com.can.zupuserservice.core.security.encryption.abstracts.IPasswordEncryptor;
import com.can.zupuserservice.core.security.encryption.jasypt.JasyptEncryptionAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptionConfig {

    @Bean
    IPasswordEncryptor getPasswordEncryptor() {
        return new JasyptEncryptionAdapter();
    }

}
