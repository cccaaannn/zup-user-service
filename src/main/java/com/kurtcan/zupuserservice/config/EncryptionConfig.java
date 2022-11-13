package com.kurtcan.zupuserservice.config;

import com.kurtcan.zupuserservice.core.security.encryption.abstracts.IPasswordEncryptor;
import com.kurtcan.zupuserservice.core.security.encryption.jasypt.JasyptEncryptionAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptionConfig {

    @Bean
    IPasswordEncryptor getPasswordEncryptor() {
        return new JasyptEncryptionAdapter();
    }

}
