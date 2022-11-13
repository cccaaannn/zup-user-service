package com.kurtcan.zupuserservice.controller.api.v1;

import com.kurtcan.zupuserservice.core.controller.abstracts.BaseController;
import com.kurtcan.zupuserservice.data.dto.auth.LoginDTO;
import com.kurtcan.zupuserservice.data.dto.user.UserAddDTO;
import com.kurtcan.zupuserservice.service.abstracts.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("${api.path.prefix}/auth")
public class AuthController extends BaseController {

    private final IAuthService authService;

    @Autowired
    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        return httpResult(authService.login(loginDTO));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserAddDTO userAddDTO) {
        return httpResult(authService.signup(userAddDTO));
    }

}
