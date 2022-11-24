package com.kurtcan.zupuserservice.core.utilities.result.concretes;

import com.kurtcan.zupuserservice.core.utilities.result.abstracts.IResult;
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
