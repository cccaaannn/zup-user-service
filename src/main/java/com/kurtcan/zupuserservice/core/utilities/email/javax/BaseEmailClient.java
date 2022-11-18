package com.kurtcan.zupuserservice.core.utilities.email.javax;

import com.kurtcan.zupuserservice.core.utilities.email.dtos.Email;
import com.kurtcan.zupuserservice.core.utilities.email.exceptions.EmailClientException;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public class BaseEmailClient {

    protected final Properties properties;

    public BaseEmailClient(Properties properties) {
        this.properties = properties;
    }

    public BaseEmailClient() {
        this.properties = new Properties();
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

    protected void send(String username, String password, Email email) throws EmailClientException {
        try {
            if (Objects.isNull(email.getTo())) {
                throw new EmailClientException("To list is null");
            }

            Session session = getSession(username, password);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(listToRecipientsString(email.getTo())));

            if (Objects.nonNull(email.getCc()) && !email.getCc().isEmpty()) {
                message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(listToRecipientsString(email.getCc())));
            }

            if (Objects.nonNull(email.getBcc()) && !email.getBcc().isEmpty()) {
                message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(listToRecipientsString(email.getBcc())));
            }

            message.setSubject(email.getSubject());

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(email.getBody(), "text/html; charset=utf-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            if (Objects.nonNull(email.getAttachments()) && !email.getAttachments().isEmpty()) {
                for (File file : email.getAttachments()) {
                    try {
                        MimeBodyPart attachmentPart = new MimeBodyPart();
                        attachmentPart.attachFile(file);
                        multipart.addBodyPart(attachmentPart);
                    } catch (IOException e) {
                        log.warn("Can not attach file {}, error: {}", file.getName(), e.getMessage());
                    }
                }
            }
            message.setContent(multipart);

            Transport.send(message);
            log.info("Email sent");

        } catch (MessagingException e) {
            throw new EmailClientException();
        }
    }
}
