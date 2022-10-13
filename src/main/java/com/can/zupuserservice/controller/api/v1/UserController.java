package com.can.zupuserservice.controller.api.v1;

import com.can.zupuserservice.core.controller.abstracts.BaseController;
import com.can.zupuserservice.core.data.dto.SortParamsDTO;
import com.can.zupuserservice.data.dto.user.UserAddDTO;
import com.can.zupuserservice.data.dto.user.UserDeleteDTO;
import com.can.zupuserservice.data.dto.user.UserUpdateDTO;
import com.can.zupuserservice.service.abstracts.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("${api.path.prefix}/users")
public class UserController extends BaseController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam Integer page, @RequestParam Integer size, @RequestParam String sort, @RequestParam String order) {
        return httpResult(userService.getAll(new SortParamsDTO(page, size, sort, order)));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getById(@PathVariable Long userId) {
        return httpResult(userService.getById(userId));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable String username) {
        return httpResult(userService.getByUsername(username));
    }

    @PostMapping("")
    public ResponseEntity<?> add(@Valid @RequestBody UserAddDTO userAddDTO) {
        return httpResult(userService.add(userAddDTO));
    }

    @PutMapping("")
    public ResponseEntity<?> update(@Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        return httpResult(userService.update(userUpdateDTO));
    }

    @DeleteMapping("")
    public ResponseEntity<?> delete(@Valid @RequestBody UserDeleteDTO userDeleteDTO) {
        return httpResult(userService.delete(userDeleteDTO));
    }

}
