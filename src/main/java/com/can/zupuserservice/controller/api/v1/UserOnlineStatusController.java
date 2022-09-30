package com.can.zupuserservice.controller.api.v1;

import com.can.zupuserservice.core.controller.abstracts.BaseController;
import com.can.zupuserservice.core.data.enums.OnlineStatus;
import com.can.zupuserservice.service.abstracts.IUserOnlineStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserOnlineStatusController extends BaseController {

    private final IUserOnlineStatusService userOnlineStatusService;

    @Autowired
    public UserOnlineStatusController(IUserOnlineStatusService userOnlineStatusService) {
        this.userOnlineStatusService = userOnlineStatusService;
    }

    @PutMapping("/{id}/online-status/{newStatus}")
    public ResponseEntity<?> setUserOnlineStatus(@PathVariable Long id, @PathVariable OnlineStatus newStatus) {
        return httpResult(userOnlineStatusService.setUserOnlineStatus(id, newStatus));
    }

    @GetMapping("/{id}/online-status")
    public ResponseEntity<?> getUserOnlineStatus(@PathVariable Long id) {
        return httpResult(userOnlineStatusService.getUserOnlineStatus(id));
    }

}
