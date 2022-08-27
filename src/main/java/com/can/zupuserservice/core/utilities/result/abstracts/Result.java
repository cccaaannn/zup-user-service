package com.can.zupuserservice.core.utilities.result.abstracts;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Result implements IResult {
    private Boolean status;
    private String message = "";

    public Result(boolean status) {
        this.status = status;
    }
}
