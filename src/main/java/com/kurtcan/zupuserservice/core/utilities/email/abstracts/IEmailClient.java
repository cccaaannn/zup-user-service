package com.kurtcan.zupuserservice.core.utilities.email.abstracts;

import com.kurtcan.zupuserservice.core.utilities.email.dtos.Email;
import com.kurtcan.zupuserservice.core.utilities.email.exceptions.EmailClientException;

public interface IEmailClient {
    void send(Email email) throws EmailClientException;
}
