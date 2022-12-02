package com.kurtcan.zupuserservice.controller.api.v1;

import com.kurtcan.javacore.security.jwt.data.dto.JWTToken;
import com.kurtcan.javacore.utilities.http.response.concrete.HttpApiResponseBuilder;
import com.kurtcan.zupuserservice.data.dto.auth.PasswordResetDTO;
import com.kurtcan.zupuserservice.data.dto.auth.UserEmailDTO;
import com.kurtcan.zupuserservice.service.abstracts.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("${api.path.prefix}/account")
public class AccountController {

    private final IAuthService authService;

    @Autowired
    public AccountController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/send-verification")
    public ResponseEntity<?> sendVerifyAccountEmail(@Valid @RequestBody UserEmailDTO userEmailDTO) {
        return HttpApiResponseBuilder.toHttpResponse(authService.sendVerifyAccountEmail(userEmailDTO));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyAccount(@Valid @RequestBody JWTToken jwtToken) {
        return HttpApiResponseBuilder.toHttpResponse(authService.verifyAccount(jwtToken));
    }

    @PostMapping("/password/send-reset")
    public ResponseEntity<?> sendForgetPasswordEmail(@Valid @RequestBody UserEmailDTO userEmailDTO) {
        return HttpApiResponseBuilder.toHttpResponse(authService.sendForgetPasswordEmail(userEmailDTO));
    }

    @PostMapping("/password/reset")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody PasswordResetDTO passwordResetDTO) {
        return HttpApiResponseBuilder.toHttpResponse(authService.resetPassword(passwordResetDTO));
    }

}
