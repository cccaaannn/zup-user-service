package com.can.zupuserservice.core.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordValidationResult {
    private Boolean status;
    private PasswordConstraints passwordConstraints;
}
