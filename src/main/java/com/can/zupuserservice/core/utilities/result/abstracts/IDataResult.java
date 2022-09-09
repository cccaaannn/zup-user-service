package com.can.zupuserservice.core.utilities.result.abstracts;

public interface IDataResult<T> extends IResult {
    T getData();
    Result toResult();
}
