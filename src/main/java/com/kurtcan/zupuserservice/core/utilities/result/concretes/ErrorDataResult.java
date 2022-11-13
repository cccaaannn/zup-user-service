package com.kurtcan.zupuserservice.core.utilities.result.concretes;

import com.kurtcan.zupuserservice.core.utilities.result.abstracts.DataResult;
import com.kurtcan.zupuserservice.core.utilities.result.abstracts.IDataResult;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ErrorDataResult<T> extends DataResult<T> implements IDataResult<T> {

    private Map<String, String> errors;

    public ErrorDataResult(T data, String message, Map<String, String> errors) {
        super(false, message, data);
        this.errors = errors;
    }

    public ErrorDataResult(String message, Map<String, String> errors) {
        super(false, message);
        this.errors = errors;
    }

    public ErrorDataResult(T data, String message) {
        super(false, message, data);
    }

    public ErrorDataResult(String message) {
        super(false, message);
    }

    public ErrorDataResult() {
        super(false);
    }
}
