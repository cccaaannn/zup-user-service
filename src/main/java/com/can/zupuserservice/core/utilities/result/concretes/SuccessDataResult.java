package com.can.zupuserservice.core.utilities.result.concretes;

import com.can.zupuserservice.core.utilities.result.abstracts.*;
import lombok.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SuccessDataResult<T> extends DataResult<T> implements IDataResult<T> {
    public SuccessDataResult(T data, String message) {
        super(true, message, data);
    }

    public SuccessDataResult(T data) {
        super(true, "", data);
    }
    public SuccessDataResult(String message) {
        super(true, message);
    }

    public SuccessDataResult() {
        super(true);
    }
}
