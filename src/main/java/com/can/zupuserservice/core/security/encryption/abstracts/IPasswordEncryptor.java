package com.can.zupuserservice.core.security.encryption.abstracts;

public interface IPasswordEncryptor {
    String encrypt(String rawPassword);
    boolean matches(String rawPassword, String encryptedPassword);
}
