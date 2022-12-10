package com.kurtcan.zupuserservice.service.concretes;

import com.kurtcan.javacore.security.jwt.data.dto.JWTToken;
import com.kurtcan.javacore.utilities.email.abstracts.IEmailClient;
import com.kurtcan.javacore.utilities.email.dtos.Email;
import com.kurtcan.zupuserservice.data.dto.TemplateEmailDTO;
import com.kurtcan.zupuserservice.data.dto.TokenPayload;
import com.kurtcan.zupuserservice.data.entity.User;
import com.kurtcan.zupuserservice.data.enums.TokenType;
import com.kurtcan.zupuserservice.service.abstracts.IEmailUtilsService;
import com.kurtcan.zupuserservice.util.MessageUtils;
import com.kurtcan.zupuserservice.util.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Service
public class EmailUtilsService implements IEmailUtilsService {

    private final IEmailClient emailClient;
    private final TokenUtils tokenUtils;
    private final SpringTemplateEngine templateEngine;
    private final MessageUtils messageUtils;

    @Value("${frontend.base_url}")
    String frontendBaseUrl;

    @Value("${frontend.reset_password_path}")
    String resetPasswordPath;

    @Value("${frontend.verify_account_path}")
    String verifyAccountPath;

    @Autowired
    public EmailUtilsService(IEmailClient emailClient, TokenUtils tokenUtils, SpringTemplateEngine templateEngine, MessageUtils messageUtils) {
        this.emailClient = emailClient;
        this.tokenUtils = tokenUtils;
        this.templateEngine = templateEngine;
        this.messageUtils = messageUtils;
    }

    @Async
    @Override
    public void sendVerifyAccountEmail(User user, Locale locale) {
        TemplateEmailDTO templateEmailDTO = new TemplateEmailDTO();
        templateEmailDTO.setEmailSubject(messageUtils.getMessage(locale, "email.activate-account.subject"));
        templateEmailDTO.setEmailTemplate("verify-account-email-template");
        templateEmailDTO.setTo(user.getEmail());

        TokenPayload tokenPayload = new TokenPayload(user, TokenType.ACCOUNT_ACTIVATION);
        JWTToken jwtToken = tokenUtils.generateToken(tokenPayload);

        String url = frontendBaseUrl + verifyAccountPath + "?token=" + jwtToken.getToken();

        Map<String, Object> properties = new HashMap<>();
        properties.put("header", messageUtils.getMessage(locale, "email.activate-account.body.header"));
        properties.put("header_sub", messageUtils.getMessage(locale, "email.activate-account.body.header-sub"));
        properties.put("welcome", messageUtils.getMessage(locale, "email.activate-account.body.welcome"));
        properties.put("greet", messageUtils.getMessage(locale, "email.activate-account.body.greet"));
        properties.put("button", messageUtils.getMessage(locale, "email.activate-account.body.button"));
        properties.put("detail", messageUtils.getMessage(locale, "email.activate-account.body.detail"));
        properties.put("USERNAME", user.getUsername());
        properties.put("URL", url);

        templateEmailDTO.setProperties(properties);

        sendTemplateEmail(templateEmailDTO);
    }

    @Async
    @Override
    public void sendResetPasswordEmail(User user, Locale locale) {
        TemplateEmailDTO templateEmailDTO = new TemplateEmailDTO();
        templateEmailDTO.setEmailSubject(messageUtils.getMessage(locale, "email.reset-password.subject"));
        templateEmailDTO.setEmailTemplate("reset-password-email-template");
        templateEmailDTO.setTo(user.getEmail());

        TokenPayload tokenPayload = new TokenPayload(user, TokenType.PASSWORD_RESET);
        JWTToken jwtToken = tokenUtils.generateToken(tokenPayload);

        String url = frontendBaseUrl + resetPasswordPath + "?token=" + jwtToken.getToken();
        Map<String, Object> properties = new HashMap<>();
        properties.put("header", messageUtils.getMessage(locale, "email.reset-password.body.header"));
        properties.put("header_sub", messageUtils.getMessage(locale, "email.reset-password.body.header-sub"));
        properties.put("greet", messageUtils.getMessage(locale, "email.reset-password.body.greet"));
        properties.put("button", messageUtils.getMessage(locale, "email.reset-password.body.button"));
        properties.put("detail", messageUtils.getMessage(locale, "email.reset-password.body.detail"));
        properties.put("USERNAME", user.getUsername());
        properties.put("URL", url);

        templateEmailDTO.setProperties(properties);

        sendTemplateEmail(templateEmailDTO);
    }

    private boolean sendTemplateEmail(TemplateEmailDTO templateEmailDTO) {
        Context context = new Context();
        context.setVariables(templateEmailDTO.getProperties());
        String parsedHtml = templateEngine.process(templateEmailDTO.getEmailTemplate(), context);
        try {
            Email email = Email.builder().to(List.of(templateEmailDTO.getTo())).subject(templateEmailDTO.getEmailSubject()).body(parsedHtml).build();
            emailClient.send(email);
            log.info("Email sent to {}", templateEmailDTO.getTo());
        } catch (Exception e) {
            log.warn(e.getMessage());
            return false;
        }
        return true;
    }

}
