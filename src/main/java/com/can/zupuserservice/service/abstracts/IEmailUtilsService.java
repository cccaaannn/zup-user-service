package com.can.zupuserservice.service.abstracts;

import com.can.zupuserservice.data.entity.User;

import java.util.Locale;

public interface IEmailUtilsService {
    void sendVerifyAccountEmail(User user, Locale locale);

    void sendResetPasswordEmail(User user, Locale locale);
}
