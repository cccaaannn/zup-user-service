package com.kurtcan.zupuserservice.core.utilities.email.javax.yandex;

import com.kurtcan.zupuserservice.core.utilities.email.abstracts.IDynamicEmailClient;
import com.kurtcan.zupuserservice.core.utilities.email.javax.JavaxDynamicEmailClient;

public class JavaxDynamicYandexClient extends JavaxDynamicEmailClient implements IDynamicEmailClient {
    public JavaxDynamicYandexClient() {
        super("smtp.yandex.ru", "465");
    }
}
