package com.can.zupuserservice.controller.api.v1;


import com.can.zupuserservice.core.controller.abstracts.BaseController;
import com.can.zupuserservice.core.security.jwt.data.dto.JWTToken;
import com.can.zupuserservice.service.abstracts.IAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.path.prefix}/authorization")
public class AuthorizationController extends BaseController {

    private final IAuthorizationService authorizationService;

    @Autowired
    public AuthorizationController(IAuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @GetMapping("/{token}")
    public ResponseEntity<?> isAuthorizedGet(@PathVariable String token) {
        return httpResult(authorizationService.isAuthorized(new JWTToken(token)));
    }

    @PostMapping("")
    public ResponseEntity<?> isAuthorizedPost(@RequestBody JWTToken jwtToken) {
        return httpResult(authorizationService.isAuthorized(jwtToken));
    }

}
