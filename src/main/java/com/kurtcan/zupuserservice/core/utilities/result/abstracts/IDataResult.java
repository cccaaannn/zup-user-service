package com.kurtcan.zupuserservice.core.utilities.result.abstracts;

import com.kurtcan.zupuserservice.core.utilities.result.concretes.Result;

public interface IDataResult<T> extends IResult {
    T getData();
    Result toResult();
}
