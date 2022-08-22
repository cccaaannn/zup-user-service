package com.can.zupuserservice.service.concretes;

import com.can.zupuserservice.core.exception.NotFoundException;
import com.can.zupuserservice.core.utilities.result.abstracts.DataResult;
import com.can.zupuserservice.core.utilities.result.abstracts.Result;
import com.can.zupuserservice.core.utilities.result.concretes.ErrorResult;
import com.can.zupuserservice.core.utilities.result.concretes.SuccessDataResult;
import com.can.zupuserservice.core.utilities.result.concretes.SuccessResult;
import com.can.zupuserservice.data.entity.Role;
import com.can.zupuserservice.repository.RoleRepository;
import com.can.zupuserservice.service.abstracts.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public DataResult<Role> getByName(String name) {
        return new SuccessDataResult<>(roleRepository.findByName(name).orElseThrow(NotFoundException::new));
    }

    @Override
    public DataResult<List<Role>> getAll() {
        return new SuccessDataResult<>(roleRepository.findAll());
    }

    @Override
    @Transactional
    public Result add(Role role) {
        if(roleRepository.findByName(role.getName()).isPresent()) {
           return new ErrorResult("Role %s already exists".formatted(role.getName()));
        }
        roleRepository.save(role);
        return new SuccessResult("Role %s added".formatted(role.getName()));
    }

}
