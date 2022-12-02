package com.kurtcan.zupuserservice.controller.api.v1;

import com.kurtcan.javacore.data.enums.PageOrder;
import com.kurtcan.javacore.security.aspects.annotations.SecuredRoute;
import com.kurtcan.javacore.security.aspects.annotations.SecuredRouteWithClaim;
import com.kurtcan.javacore.utilities.http.response.concrete.HttpApiResponseBuilder;
import com.kurtcan.zupuserservice.data.dto.user.UserDeleteDTO;
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
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @SecuredRoute
    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page", defaultValue = "0", required = true) @Min(0) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = true) @Min(1) Integer size,
            @RequestParam(name = "sort", defaultValue = "id", required = true) UserSort sort,
            @RequestParam(name = "order", defaultValue = "desc", required = true) PageOrder order,
            @RequestParam(name = "ids", defaultValue = "", required = false) List<Long> ids
    ) {
        return HttpApiResponseBuilder.toHttpResponse(userService.getAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order.ORDER_NAME), sort.SORT_NAME)), ids));
    }

    @SecuredRoute
    @GetMapping("/{userId}")
    public ResponseEntity<?> getById(@PathVariable Long userId) {
        return HttpApiResponseBuilder.toHttpResponse(userService.getById(userId));
    }

    @SecuredRoute
    @GetMapping("/username/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable String username) {
        return HttpApiResponseBuilder.toHttpResponse(userService.getByUsername(username));
    }

//    @PostMapping("")
//    public ResponseEntity<?> add(@Valid @RequestBody UserAddDTO userAddDTO) {
//        return HttpApiResponseBuilder.toHttpResponse(userService.add(userAddDTO));
//    }
//
//    @PutMapping("")
//    public ResponseEntity<?> update(@Valid @RequestBody UserUpdateDTO userUpdateDTO) {
//        return HttpApiResponseBuilder.toHttpResponse(userService.update(userUpdateDTO));
//    }

    @SecuredRouteWithClaim({"ADMIN", "SYS_ADMIN"})
    @PatchMapping("/{userId}/activate")
    public ResponseEntity<?> activate(@PathVariable Long userId) {
        return HttpApiResponseBuilder.toHttpResponse(userService.activateUser(userId));
    }

    @SecuredRouteWithClaim({"ADMIN", "SYS_ADMIN"})
    @PatchMapping("/{userId}/suspend")
    public ResponseEntity<?> suspend(@PathVariable Long userId) {
        return HttpApiResponseBuilder.toHttpResponse(userService.suspendUser(userId));
    }

    @SecuredRouteWithClaim({"SYS_ADMIN"})
    @DeleteMapping("")
    public ResponseEntity<?> delete(@Valid @RequestBody UserDeleteDTO userDeleteDTO) {
        return HttpApiResponseBuilder.toHttpResponse(userService.delete(userDeleteDTO));
    }

}
