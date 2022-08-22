package com.can.zupuserservice.service.abstracts;

import com.can.zupuserservice.core.utilities.result.abstracts.DataResult;
import com.can.zupuserservice.core.utilities.result.abstracts.Result;
import com.can.zupuserservice.data.entity.Role;

import java.util.List;

public interface IRoleService {
    DataResult<Role> getByName(String name);

    DataResult<List<Role>> getAll();

    Result add(Role role);
}
