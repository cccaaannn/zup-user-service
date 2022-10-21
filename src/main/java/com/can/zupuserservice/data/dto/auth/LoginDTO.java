package com.can.zupuserservice.data.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotNull
    @Size(min = 3, max = 30, message = "Size error")
    @NotBlank(message = "Can not be blank")
    private String username;

    @NotNull
    @NotBlank(message = "Can not be blank")
    private String password;

}
