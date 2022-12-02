package com.kurtcan.zupuserservice.controller.api.v1;

import com.kurtcan.javacore.data.enums.OnlineStatus;
import com.kurtcan.javacore.security.aspects.annotations.SecuredRoute;
import com.kurtcan.javacore.utilities.http.response.concrete.HttpApiResponseBuilder;
import com.kurtcan.zupuserservice.service.abstracts.IUserOnlineStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.path.prefix}/users")
public class UserOnlineStatusController {

    private final IUserOnlineStatusService userOnlineStatusService;

    @Autowired
    public UserOnlineStatusController(IUserOnlineStatusService userOnlineStatusService) {
        this.userOnlineStatusService = userOnlineStatusService;
    }

    @SecuredRoute
    @PutMapping("/{id}/online-status/{newStatus}")
    public ResponseEntity<?> setUserOnlineStatus(@PathVariable Long id, @PathVariable OnlineStatus newStatus) {
        return HttpApiResponseBuilder.toHttpResponse(userOnlineStatusService.setUserOnlineStatus(id, newStatus));
    }

    @SecuredRoute
    @GetMapping("/{id}/online-status")
    public ResponseEntity<?> getUserOnlineStatus(@PathVariable Long id) {
        return HttpApiResponseBuilder.toHttpResponse(userOnlineStatusService.getUserOnlineStatus(id));
    }

}
