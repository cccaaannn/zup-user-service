package com.kurtcan.zupuserservice.core.utilities.email.javax;


import com.kurtcan.zupuserservice.core.utilities.email.abstracts.IDynamicEmailClient;
import com.kurtcan.zupuserservice.core.utilities.email.dtos.Email;
import com.kurtcan.zupuserservice.core.utilities.email.exceptions.EmailClientException;

import java.util.List;
import java.util.Properties;

public class JavaxDynamicEmailClient extends BaseEmailClient implements IDynamicEmailClient {

    public JavaxDynamicEmailClient(Properties properties) {
        super(properties);
    }

    public JavaxDynamicEmailClient(String host, String port) {
        super();

        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
    }

    @Override
    public void send(String username, String password, Email email) throws EmailClientException {
        super.send(username, password, email);
    }

}
