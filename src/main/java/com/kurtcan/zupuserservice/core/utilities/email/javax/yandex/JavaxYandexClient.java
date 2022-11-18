package com.kurtcan.zupuserservice.core.utilities.email.javax.yandex;

import com.kurtcan.zupuserservice.core.utilities.email.abstracts.IEmailClient;
import com.kurtcan.zupuserservice.core.utilities.email.javax.JavaxEmailClient;

public class JavaxYandexClient extends JavaxEmailClient implements IEmailClient {
    public JavaxYandexClient(String username, String password) {
        super("smtp.yandex.ru", "465", username, password);
    }
}
