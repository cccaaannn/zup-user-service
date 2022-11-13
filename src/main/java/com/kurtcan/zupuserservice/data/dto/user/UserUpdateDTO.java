package com.kurtcan.zupuserservice.data.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 3, max = 30, message = "Size error")
    @NotBlank(message = "Can not be blank")
    private String username;

//    @NotNull
    @Size(min = 3, max = 30, message = "Size error")
    @NotBlank(message = "Can not be blank")
    private String password;

}
