package com.kurtcan.zupuserservice.core.utilities.email.abstracts;

import javax.mail.MessagingException;
import javax.security.auth.login.CredentialNotFoundException;
import java.util.List;

public interface IEmailClient {
    void send(String username, String password, List<String> toList, String messageSubject, String messageBody) throws MessagingException;

    void send(String username, String password, String to, String messageSubject, String messageBody) throws MessagingException;

    void send(List<String> toList, String messageSubject, String messageBody) throws CredentialNotFoundException, MessagingException;

    void send(String to, String messageSubject, String messageBody) throws CredentialNotFoundException, MessagingException;
}
