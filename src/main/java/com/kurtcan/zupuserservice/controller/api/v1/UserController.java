package com.kurtcan.zupuserservice.controller.api.v1;

import com.kurtcan.zupuserservice.core.controller.abstracts.BaseController;
import com.kurtcan.zupuserservice.core.data.enums.PageOrder;
import com.kurtcan.zupuserservice.data.dto.user.UserAddDTO;
import com.kurtcan.zupuserservice.data.dto.user.UserDeleteDTO;
import com.kurtcan.zupuserservice.data.dto.user.UserUpdateDTO;
import com.kurtcan.zupuserservice.data.enums.UserSort;
import com.kurtcan.zupuserservice.service.abstracts.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequestMapping("${api.path.prefix}/users")
public class UserController extends BaseController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page", defaultValue = "0", required = true) @Min(0) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = true) @Min(1) Integer size,
            @RequestParam(name = "sort", defaultValue = "id", required = true) UserSort sort,
            @RequestParam(name = "order", defaultValue = "desc", required = true) PageOrder order,
            @RequestParam(name = "ids", defaultValue = "", required = false) List<Long> ids
    ) {
        return httpResult(userService.getAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order.ORDER_NAME), sort.SORT_NAME)), ids));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getById(@PathVariable Long userId) {
        return httpResult(userService.getById(userId));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable String username) {
        return httpResult(userService.getByUsername(username));
    }

//    @PostMapping("")
//    public ResponseEntity<?> add(@Valid @RequestBody UserAddDTO userAddDTO) {
//        return httpResult(userService.add(userAddDTO));
//    }
//
//    @PutMapping("")
//    public ResponseEntity<?> update(@Valid @RequestBody UserUpdateDTO userUpdateDTO) {
//        return httpResult(userService.update(userUpdateDTO));
//    }

    @PatchMapping("/{userId}/activate")
    public ResponseEntity<?> activate(@PathVariable Long userId) {
        return httpResult(userService.activateUser(userId));
    }

    @PatchMapping("/{userId}/suspend")
    public ResponseEntity<?> suspend(@PathVariable Long userId) {
        return httpResult(userService.suspendUser(userId));
    }

    @DeleteMapping("")
    public ResponseEntity<?> delete(@Valid @RequestBody UserDeleteDTO userDeleteDTO) {
        return httpResult(userService.delete(userDeleteDTO));
    }

}
