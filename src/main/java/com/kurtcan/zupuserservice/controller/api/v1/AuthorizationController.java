package com.kurtcan.zupuserservice.controller.api.v1;


import com.kurtcan.zupuserservice.core.security.jwt.data.dto.JWTToken;
import com.kurtcan.zupuserservice.core.utilities.http.response.concrete.HttpApiResponseBuilder;
import com.kurtcan.zupuserservice.service.abstracts.IAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.path.prefix}/authorization")
public class AuthorizationController {

    private final IAuthorizationService authorizationService;

    @Autowired
    public AuthorizationController(IAuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @GetMapping("/{token}")
    public ResponseEntity<?> isAuthorizedGet(@PathVariable String token) {
        return HttpApiResponseBuilder.toHttpResponse(authorizationService.isAuthorized(new JWTToken(token)));
    }

    @PostMapping("")
    public ResponseEntity<?> isAuthorizedPost(@RequestBody JWTToken jwtToken) {
        return HttpApiResponseBuilder.toHttpResponse(authorizationService.isAuthorized(jwtToken));
    }

}
