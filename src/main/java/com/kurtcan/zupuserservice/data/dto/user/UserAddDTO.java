package com.kurtcan.zupuserservice.data.dto.user;

import com.kurtcan.zupuserservice.core.security.validation.password.aspects.annotations.StrongPassword;
import com.kurtcan.zupuserservice.aspect.annotation.UniqueEmail;
import com.kurtcan.zupuserservice.aspect.annotation.UniqueUsername;
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
public class UserAddDTO {

    @NotNull
    @Size(min = 3, max = 30, message = "Size error")
    @NotBlank(message = "Can not be blank")
    @UniqueUsername
    private String username;

    @NotNull
    @Size(max = 150, message = "Size error")
    @Email(message = "Not a valid email")
    @NotBlank(message = "Can not be blank")
    @UniqueEmail
    private String email;

    @StrongPassword(usePropertyKeyOnMessages = true)
    private String password;

}
