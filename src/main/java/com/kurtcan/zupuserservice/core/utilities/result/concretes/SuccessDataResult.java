package com.kurtcan.zupuserservice.core.utilities.result.concretes;

import com.kurtcan.zupuserservice.core.utilities.result.abstracts.DataResult;
import com.kurtcan.zupuserservice.core.utilities.result.abstracts.IDataResult;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
