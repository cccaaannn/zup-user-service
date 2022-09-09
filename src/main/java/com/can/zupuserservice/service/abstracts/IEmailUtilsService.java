package com.can.zupuserservice.service.abstracts;

import com.can.zupuserservice.data.entity.User;

public interface IEmailUtilsService {
    void sendVerifyAccountEmail(User user);

    void sendResetPasswordEmail(User user);
}
