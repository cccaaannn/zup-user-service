package com.can.zupuserservice.data.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotNull
    @Size(max = 150, message = "Size error")
    @Email(message = "Not a valid email")
    @NotBlank(message = "Can not be blank")
    private String email;

    @NotNull
    @NotBlank(message = "Can not be blank")
    private String password;

}
