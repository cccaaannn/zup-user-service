package com.kurtcan.zupuserservice.core.utilities.result.concretes;

import com.kurtcan.zupuserservice.core.utilities.result.abstracts.IResult;
import com.kurtcan.zupuserservice.core.utilities.result.abstracts.Result;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
