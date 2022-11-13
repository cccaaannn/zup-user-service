package com.kurtcan.zupuserservice.service.concretes;

import com.kurtcan.zupuserservice.core.exception.NotFoundException;
import com.kurtcan.zupuserservice.core.utilities.result.abstracts.DataResult;
import com.kurtcan.zupuserservice.core.utilities.result.abstracts.Result;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.ErrorResult;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.SuccessDataResult;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.SuccessResult;
import com.kurtcan.zupuserservice.data.entity.Role;
import com.kurtcan.zupuserservice.repository.RoleRepository;
import com.kurtcan.zupuserservice.service.abstracts.IRoleService;
import com.kurtcan.zupuserservice.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;
    private final MessageUtils messageUtils;

    @Autowired
    public RoleService(RoleRepository roleRepository, MessageUtils messageUtils) {
        this.roleRepository = roleRepository;
        this.messageUtils = messageUtils;
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
           return new ErrorResult(messageUtils.getMessage("role.error.already-exists", role.getName()));
        }
        roleRepository.save(role);
        return new SuccessResult(messageUtils.getMessage("role.success.added", role.getName()));
    }

}
