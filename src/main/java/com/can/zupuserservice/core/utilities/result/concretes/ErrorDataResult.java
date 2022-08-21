package com.can.zupuserservice.core.utilities.result.concretes;

import com.can.zupuserservice.core.utilities.result.abstracts.*;
import lombok.*;

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
