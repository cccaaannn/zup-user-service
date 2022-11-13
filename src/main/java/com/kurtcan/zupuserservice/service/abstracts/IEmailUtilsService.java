package com.kurtcan.zupuserservice.service.abstracts;

import com.kurtcan.zupuserservice.data.entity.User;

import java.util.Locale;

public interface IEmailUtilsService {
    void sendVerifyAccountEmail(User user, Locale locale);

    void sendResetPasswordEmail(User user, Locale locale);
}
