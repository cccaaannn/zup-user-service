package com.can.zupuserservice.core.result.concretes;

import com.can.zupuserservice.core.result.abstracts.*;
import lombok.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ErrorDataResult<T> extends DataResult<T> implements IDataResult<T> {
    public ErrorDataResult(String message, T data) {
        super(false, message, data);
    }

    public ErrorDataResult(String message) {
        super(false, message);
    }

    public ErrorDataResult() {
        super(false);
    }
}
