package com.kurtcan.zupuserservice.core.utilities.email.javax.gmail;

import com.kurtcan.zupuserservice.core.utilities.email.abstracts.IDynamicEmailClient;
import com.kurtcan.zupuserservice.core.utilities.email.javax.JavaxDynamicEmailClient;

public class JavaxDynamicGmailClient extends JavaxDynamicEmailClient implements IDynamicEmailClient {
    public JavaxDynamicGmailClient() {
        super("smtp.gmail.com", "465");
    }
}
