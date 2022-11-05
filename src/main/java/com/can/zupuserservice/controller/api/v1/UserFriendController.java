package com.can.zupuserservice.controller.api.v1;

import com.can.zupuserservice.core.controller.abstracts.BaseController;
import com.can.zupuserservice.data.dto.UserFriend.UserFriendAddDeleteDTO;
import com.can.zupuserservice.service.abstracts.IUserFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("${api.path.prefix}/users/friends")
public class UserFriendController extends BaseController {

    private final IUserFriendService userFriendService;

    @Autowired
    public UserFriendController(IUserFriendService userFriendService) {
        this.userFriendService = userFriendService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllFriends() {
        return httpResult(userFriendService.getFriends());
    }

    @PutMapping("/toggle")
    public ResponseEntity<?> toggleFriend(@Valid @RequestBody UserFriendAddDeleteDTO userFriendAddDeleteDTO) {
        return httpResult(userFriendService.toggleFriend(userFriendAddDeleteDTO));
    }

    @PostMapping("")
    public ResponseEntity<?> addByFriendId(@Valid @RequestBody UserFriendAddDeleteDTO userFriendAddDeleteDTO) {
        return httpResult(userFriendService.addByFriendId(userFriendAddDeleteDTO));
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteByFriendId(@Valid @RequestBody UserFriendAddDeleteDTO userFriendAddDeleteDTO) {
        return httpResult(userFriendService.deleteByFriendId(userFriendAddDeleteDTO));
    }

}
