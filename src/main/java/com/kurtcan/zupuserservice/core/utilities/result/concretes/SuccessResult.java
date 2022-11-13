package com.kurtcan.zupuserservice.core.utilities.result.concretes;

import com.kurtcan.zupuserservice.core.utilities.result.abstracts.IResult;
import com.kurtcan.zupuserservice.core.utilities.result.abstracts.Result;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SuccessResult extends Result implements IResult {
    public SuccessResult(String message) {
        super(true, message);
    }

    public SuccessResult() {
        super(true);
    }
}
