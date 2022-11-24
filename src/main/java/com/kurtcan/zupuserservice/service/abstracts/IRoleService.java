package com.kurtcan.zupuserservice.service.abstracts;

import com.kurtcan.zupuserservice.core.utilities.result.concretes.DataResult;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.Result;
import com.kurtcan.zupuserservice.data.entity.Role;

import java.util.List;

public interface IRoleService {
    DataResult<Role> getByName(String name);

    DataResult<List<Role>> getAll();

    Result add(Role role);
}
