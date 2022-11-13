package com.kurtcan.zupuserservice.core.utilities.email.concretes;

import com.kurtcan.zupuserservice.core.utilities.email.abstracts.IEmailClient;

public class GmailClient extends EmailClient implements IEmailClient {

    public GmailClient() {
        super("smtp.gmail.com", "465");
    }

    public GmailClient(String username, String password) {
        super("smtp.gmail.com", "465", username, password);
    }

}
