package com.kurtcan.zupuserservice.core.security.encryption.jasypt;

import com.kurtcan.zupuserservice.core.security.encryption.abstracts.IPasswordEncryptor;
import org.jasypt.util.password.BasicPasswordEncryptor;

public class JasyptEncryptionAdapter implements IPasswordEncryptor {

    BasicPasswordEncryptor encryptor;

    public JasyptEncryptionAdapter() {
        this.encryptor = new BasicPasswordEncryptor();
    }

    public String encrypt(String rawPassword) {
        return encryptor.encryptPassword(rawPassword.toString());
    }

    public boolean matches(String rawPassword, String encryptedPassword) {
        return encryptor.checkPassword(rawPassword, encryptedPassword);
    }

}
