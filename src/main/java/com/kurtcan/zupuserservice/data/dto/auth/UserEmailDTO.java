package com.kurtcan.zupuserservice.data.dto.auth;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEmailDTO {

    @NotNull
    @Size(max = 150, message = "Size error")
    @Email(message = "Not a valid email")
    @NotBlank(message = "Can not be blank")
    private String email;

}
