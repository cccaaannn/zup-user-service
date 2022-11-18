package com.kurtcan.zupuserservice.core.utilities.email.javax.gmail;

import com.kurtcan.zupuserservice.core.utilities.email.abstracts.IEmailClient;
import com.kurtcan.zupuserservice.core.utilities.email.javax.JavaxEmailClient;

public class JavaxGmailClient extends JavaxEmailClient implements IEmailClient {
    public JavaxGmailClient(String username, String password) {
        super("smtp.gmail.com", "465", username, password);
    }
}
