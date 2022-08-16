package com.can.zupuserservice.core.result.concretes;

import com.can.zupuserservice.core.result.abstracts.*;
import lombok.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ErrorResult extends Result implements IResult {
    public ErrorResult(String message) {
        super(false, message);
    }

    public ErrorResult() {
        super(false);
    }
}
