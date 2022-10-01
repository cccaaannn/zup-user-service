package com.can.zupuserservice.controller.api.v1;


import com.can.zupuserservice.core.controller.abstracts.BaseController;
import com.can.zupuserservice.core.data.dto.AccessToken;
import com.can.zupuserservice.service.abstracts.IAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authorization")
public class AuthorizationController extends BaseController {

    private final IAuthorizationService authorizationService;

    @Autowired
    public AuthorizationController(IAuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @GetMapping("/{token}")
    public ResponseEntity<?> isAuthorizedGet(@PathVariable String token) {
        return httpResult(authorizationService.isAuthorized(new AccessToken(token)));
    }

    @PostMapping("")
    public ResponseEntity<?> isAuthorizedPost(@RequestBody AccessToken accessToken) {
        return httpResult(authorizationService.isAuthorized(accessToken));
    }

}
