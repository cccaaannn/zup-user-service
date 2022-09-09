package com.can.zupuserservice.core.controller.abstracts;

import com.can.zupuserservice.core.utilities.result.abstracts.IResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public abstract class BaseController implements IBaseController {

    public ResponseEntity<?> httpResult(IResult result) {
        if(result.getStatus()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

}
