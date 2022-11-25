package com.kurtcan.zupuserservice.controller.api.v1;

import com.kurtcan.zupuserservice.core.utilities.http.response.concrete.HttpApiResponseBuilder;
import com.kurtcan.zupuserservice.data.dto.UserFriend.UserFriendAddDeleteDTO;
import com.kurtcan.zupuserservice.service.abstracts.IUserFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("${api.path.prefix}/users/friends")
public class UserFriendController {

    private final IUserFriendService userFriendService;

    @Autowired
    public UserFriendController(IUserFriendService userFriendService) {
        this.userFriendService = userFriendService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllFriends() {
        return HttpApiResponseBuilder.toHttpResponse(userFriendService.getFriends());
    }

    @PutMapping("/toggle")
    public ResponseEntity<?> toggleFriend(@Valid @RequestBody UserFriendAddDeleteDTO userFriendAddDeleteDTO) {
        return HttpApiResponseBuilder.toHttpResponse(userFriendService.toggleFriend(userFriendAddDeleteDTO));
    }

    @PostMapping("")
    public ResponseEntity<?> addByFriendId(@Valid @RequestBody UserFriendAddDeleteDTO userFriendAddDeleteDTO) {
        return HttpApiResponseBuilder.toHttpResponse(userFriendService.addByFriendId(userFriendAddDeleteDTO));
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteByFriendId(@Valid @RequestBody UserFriendAddDeleteDTO userFriendAddDeleteDTO) {
        return HttpApiResponseBuilder.toHttpResponse(userFriendService.deleteByFriendId(userFriendAddDeleteDTO));
    }

}
