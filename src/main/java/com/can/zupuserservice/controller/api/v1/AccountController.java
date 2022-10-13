package com.can.zupuserservice.controller.api.v1;

import com.can.zupuserservice.core.controller.abstracts.BaseController;
import com.can.zupuserservice.core.data.dto.AccessToken;
import com.can.zupuserservice.data.dto.auth.PasswordResetDTO;
import com.can.zupuserservice.data.dto.auth.UserEmailDTO;
import com.can.zupuserservice.service.abstracts.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("${api.path.prefix}/account")
public class AccountController extends BaseController {

    private final IAuthService authService;

    @Autowired
    public AccountController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/send-verification")
    public ResponseEntity<?> sendVerifyAccountEmail(@Valid @RequestBody UserEmailDTO userEmailDTO) {
        return httpResult(authService.sendVerifyAccountEmail(userEmailDTO));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyAccount(@Valid @RequestBody AccessToken accessToken) {
        return httpResult(authService.verifyAccount(accessToken));
    }

    @PostMapping("/password/send-reset")
    public ResponseEntity<?> sendForgetPasswordEmail(@Valid @RequestBody UserEmailDTO userEmailDTO) {
        return httpResult(authService.sendForgetPasswordEmail(userEmailDTO));
    }

    @PostMapping("/password/reset")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody PasswordResetDTO passwordResetDTO) {
        return httpResult(authService.resetPassword(passwordResetDTO));
    }

}
