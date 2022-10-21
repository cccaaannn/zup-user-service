package com.can.zupuserservice.service.concretes;

import com.can.zupuserservice.core.data.dto.AccessToken;
import com.can.zupuserservice.core.utilities.email.abstracts.IEmailClient;
import com.can.zupuserservice.data.dto.TemplateEmailDTO;
import com.can.zupuserservice.data.dto.TokenPayload;
import com.can.zupuserservice.data.entity.User;
import com.can.zupuserservice.data.enums.TokenType;
import com.can.zupuserservice.service.abstracts.IEmailUtilsService;
import com.can.zupuserservice.service.abstracts.ITokenUtilsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class EmailUtilsService implements IEmailUtilsService {

    private final IEmailClient emailClient;
    private final ITokenUtilsService tokenUtilsService;
    private final SpringTemplateEngine templateEngine;

    @Value("${frontend.base-url}")
    String frontendBaseUrl;

    @Value("${frontend.reset-password-path}")
    String resetPasswordPath;

    @Value("${frontend.verify-account-path}")
    String verifyAccountPath;

    @Autowired
    public EmailUtilsService(IEmailClient emailClient, ITokenUtilsService tokenUtilsService, SpringTemplateEngine templateEngine) {
        this.emailClient = emailClient;
        this.tokenUtilsService = tokenUtilsService;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendVerifyAccountEmail(User user) {
        TemplateEmailDTO templateEmailDTO = new TemplateEmailDTO();
        templateEmailDTO.setEmailSubject("Activate account");
        templateEmailDTO.setEmailTemplate("verify-account-email-template");
        templateEmailDTO.setTo(user.getEmail());

        TokenPayload tokenPayload = new TokenPayload(user, TokenType.ACCOUNT_ACTIVATION);
        AccessToken accessToken = tokenUtilsService.generateToken(tokenPayload);

        String url = frontendBaseUrl + verifyAccountPath + "?token=" + accessToken.getToken();

        Map<String, Object> properties = new HashMap<>();
        properties.put("USERNAME", user.getUsername());
        properties.put("URL", url);

        templateEmailDTO.setProperties(properties);

        sendTemplateEmail(templateEmailDTO);
    }

    @Async
    public void sendResetPasswordEmail(User user) {
        TemplateEmailDTO templateEmailDTO = new TemplateEmailDTO();
        templateEmailDTO.setEmailSubject("Reset password");
        templateEmailDTO.setEmailTemplate("reset-password-email-template");
        templateEmailDTO.setTo(user.getEmail());

        TokenPayload tokenPayload = new TokenPayload(user, TokenType.PASSWORD_RESET);
        AccessToken accessToken = tokenUtilsService.generateToken(tokenPayload);

        String url = frontendBaseUrl + resetPasswordPath + "?token=" + accessToken.getToken();

        Map<String, Object> properties = new HashMap<>();
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
            emailClient.send(templateEmailDTO.getTo(), templateEmailDTO.getEmailSubject(), parsedHtml);
            log.info("Email sent to %s".formatted(templateEmailDTO.getTo()));
        }
        catch (Exception e) {
            log.warn(e.getMessage());
            return false;
        }
        return true;
    }

}
