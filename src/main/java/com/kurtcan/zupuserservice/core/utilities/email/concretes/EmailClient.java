package com.kurtcan.zupuserservice.core.utilities.email.concretes;

import com.kurtcan.zupuserservice.core.utilities.email.abstracts.IEmailClient;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.security.auth.login.CredentialNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;


public class EmailClient implements IEmailClient {

    private final Properties properties;
    private String username = null;
    private String password = null;

    public EmailClient(Properties props) {
        this.properties = props;
    }

    public EmailClient(String host, String port) {
        properties = new Properties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
    }

    public EmailClient(String host, String port, String username, String password) {
        this(host, port);

        this.username = username;
        this.password = password;
    }

    private Session getSession(String username, String password) {
        return Session.getInstance(this.properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    private String listToRecipientsString(List<String> toList) {
        StringBuilder stringBuilder = new StringBuilder();
        toList.forEach(s -> stringBuilder.append(s).append(","));
        return stringBuilder.toString();
    }

    @Override
    public void send(String username, String password, List<String> toList, String messageSubject, String messageBody) throws MessagingException {
        Session session = getSession(username, password);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(listToRecipientsString(toList)));
        message.setSubject(messageSubject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(messageBody, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);

    }

    @Override
    public void send(String username, String password, String to, String messageSubject, String messageBody) throws MessagingException {
        send(username, password, List.of(to), messageSubject, messageBody);
    }

    /*
     * Uses credentials proved from constructor if exists.
     */
    @Override
    public void send(List<String> toList, String messageSubject, String messageBody) throws CredentialNotFoundException, MessagingException {
       if(Objects.isNull(this.username) || Objects.isNull(this.password)) {
           throw new CredentialNotFoundException("No credentials provided");
       }
       send(this.username, this.password, toList, messageSubject, messageBody);
    }

    @Override
    public void send(String to, String messageSubject, String messageBody) throws CredentialNotFoundException, MessagingException {
        if(Objects.isNull(this.username) || Objects.isNull(this.password)) {
            throw new CredentialNotFoundException("No credentials provided");
        }
        send(this.username, this.password, List.of(to), messageSubject, messageBody);
    }

}
