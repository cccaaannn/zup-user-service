package com.can.zupuserservice.core.utilities.result.concretes;

import com.can.zupuserservice.core.utilities.result.abstracts.*;
import lombok.*;

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
