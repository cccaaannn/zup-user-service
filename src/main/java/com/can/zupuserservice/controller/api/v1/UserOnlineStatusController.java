package com.can.zupuserservice.controller.api.v1;

import com.can.zupuserservice.core.controller.abstracts.BaseController;
import com.can.zupuserservice.service.abstracts.IUserOnlineStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/onlineStatus")
public class UserOnlineStatusController extends BaseController {

    private final IUserOnlineStatusService userOnlineStatusService;

    @Autowired
    public UserOnlineStatusController(IUserOnlineStatusService userOnlineStatusService) {
        this.userOnlineStatusService = userOnlineStatusService;
    }

    @PutMapping("/setOnline/{id}")
    public ResponseEntity<?> setUserOnline(@PathVariable Long id) {
        return httpResult(userOnlineStatusService.setUserOnline(id));
    }

    @PutMapping("/setOffline/{id}")
    public ResponseEntity<?> setUserOffline(@PathVariable Long id) {
        return httpResult(userOnlineStatusService.setUserOffline(id));
    }

}
