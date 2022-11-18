package com.kurtcan.zupuserservice.core.utilities.email.javax;

import com.kurtcan.zupuserservice.core.utilities.email.abstracts.IEmailClient;
import com.kurtcan.zupuserservice.core.utilities.email.dtos.Email;
import com.kurtcan.zupuserservice.core.utilities.email.exceptions.EmailClientException;

import java.util.Properties;

public class JavaxEmailClient extends BaseEmailClient implements IEmailClient {

    private final String username;
    private final String password;

    public JavaxEmailClient(Properties properties, String username, String password) {
        super(properties);

        this.username = username;
        this.password = password;
    }

    public JavaxEmailClient(String host, String port, String username, String password) {
        super();

        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        this.username = username;
        this.password = password;
    }

    @Override
    public void send(Email email) throws EmailClientException {
        super.send(this.username, this.password, email);
    }

}
