package com.can.zupuserservice.core.utilities.result.concretes;

import com.can.zupuserservice.core.utilities.result.abstracts.*;
import lombok.*;

import java.util.Map;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ErrorResult extends Result implements IResult {

    private Map<String, String> errors;

    public ErrorResult(String message, Map<String, String> errors) {
        super(false, message);
        this.errors = errors;
    }

    public ErrorResult(String message) {
        super(false, message);
    }

    public ErrorResult() {
        super(false);
    }
}
