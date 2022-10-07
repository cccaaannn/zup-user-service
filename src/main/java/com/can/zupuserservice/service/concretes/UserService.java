package com.can.zupuserservice.service.concretes;

import com.can.zupuserservice.core.data.dto.SortParamsDTO;
import com.can.zupuserservice.core.exception.NotFoundException;
import com.can.zupuserservice.core.utilities.result.abstracts.DataResult;
import com.can.zupuserservice.core.utilities.result.abstracts.Result;
import com.can.zupuserservice.core.utilities.result.concretes.ErrorDataResult;
import com.can.zupuserservice.core.utilities.result.concretes.ErrorResult;
import com.can.zupuserservice.core.utilities.result.concretes.SuccessDataResult;
import com.can.zupuserservice.core.utilities.result.concretes.SuccessResult;
import com.can.zupuserservice.core.security.encryption.abstracts.IPasswordEncryptor;
import com.can.zupuserservice.core.data.enums.PageOrder;
import com.can.zupuserservice.core.data.enums.UserStatus;
import com.can.zupuserservice.data.entity.Role;
import com.can.zupuserservice.data.entity.User;
import com.can.zupuserservice.data.entity.UserOnlineStatus;
import com.can.zupuserservice.data.dto.user.UserAddDTO;
import com.can.zupuserservice.data.dto.user.UserDTO;
import com.can.zupuserservice.data.dto.user.UserDeleteDTO;
import com.can.zupuserservice.data.dto.user.UserUpdateDTO;
import com.can.zupuserservice.repository.UserRepository;
import com.can.zupuserservice.service.abstracts.IRoleService;
import com.can.zupuserservice.service.abstracts.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final IRoleService roleService;
    private final IPasswordEncryptor passwordEncryptor;
    private final ModelMapper modelMapper;

    private final static List<String> sortableFields = List.of("id", "username", "email", "role");
    private final static Map<String, String> sortableFieldsMap = Map.of("id", "id", "username", "username", "email", "email", "role", "role.name");

    @Autowired
    public UserService(UserRepository userRepository, IRoleService roleService, IPasswordEncryptor passwordEncryptor, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncryptor = passwordEncryptor;
        this.modelMapper = modelMapper;
    }

    @Override
    public DataResult<Page<UserDTO>> getAll(SortParamsDTO sortParamsDTO) {
        if (!UserService.sortableFields.contains(sortParamsDTO.getSort())) {
            return new ErrorDataResult<>("No sorting option exists for %s".formatted(sortParamsDTO.getSort()));
        }

        Sort sortBy;
        if (Objects.nonNull(sortParamsDTO.getOrder()) && sortParamsDTO.getOrder().equalsIgnoreCase(PageOrder.DESC.ORDER_NAME)) {
            sortBy = Sort.by(UserService.sortableFieldsMap.get(sortParamsDTO.getSort())).descending();
        } else {
            sortBy = Sort.by(UserService.sortableFieldsMap.get(sortParamsDTO.getSort())).ascending();
        }
        Pageable pageable = PageRequest.of(sortParamsDTO.getPage(), sortParamsDTO.getSize(), sortBy);

        return new SuccessDataResult<>(userRepository.customFindAll(pageable));
    }

    @Override
    public DataResult<UserDTO> getById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setEmail(null);

        // TODO return different parts of the user for different permissions.
        return new SuccessDataResult<>(userDTO);
    }

    @Override
    public DataResult<User> getByIdInternal(Long userId) {
        return new SuccessDataResult<>(userRepository.findById(userId).orElseThrow(NotFoundException::new));
    }

    @Override
    public DataResult<UserDTO> getByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(NotFoundException::new);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setEmail(null);
        return new SuccessDataResult<>(userDTO);
    }

    @Override
    public DataResult<User> getByUsernameInternal(String username) {
        return new SuccessDataResult<>(userRepository.findByUsername(username).orElseThrow(NotFoundException::new));
    }

    @Override
    public DataResult<User> getByEmail(String email) {
        return new SuccessDataResult<>(userRepository.findByEmail(email).orElseThrow(NotFoundException::new));
    }

    @Override
    public Boolean isExistsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public Boolean isExistsByUsername(String username, Long id) {
        return userRepository.findByUsernameAndId(id, username).isPresent();
    }

    @Override
    public Boolean isExistsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public Boolean isExistsByEmail(String email, Long id) {
        return userRepository.findByEmailAndId(id, email).isPresent();
    }

    @Override
    @Transactional
    public Result activateUser(Long id) {
        userRepository.changeUserStatus(id, UserStatus.ACTIVE);
        return new SuccessResult("User activated.");
    }

    @Override
    @Transactional
    public Result suspendUser(Long id) {
        userRepository.changeUserStatus(id, UserStatus.SUSPENDED);
        return new SuccessResult("User suspended.");
    }

    @Override
    @Transactional
    public Result changePassword(Long id, String newPassword) {
        String encryptedPassword = passwordEncryptor.encrypt(newPassword);
        userRepository.updatePassword(id, encryptedPassword);
        return new SuccessResult("Password changed.");
    }

    @Override
    @Transactional
    public DataResult<User> add(UserAddDTO userAddDTO) {

//        if (isExistsByUsername(userAddDTO.getUsername())) {
//            return new ErrorResult("Username %s is taken".formatted(userAddDTO.getUsername()));
//        }
        if (isExistsByEmail(userAddDTO.getEmail())) {
            return new ErrorDataResult<>("Email %s is taken".formatted(userAddDTO.getEmail()));
        }

        UserOnlineStatus userOnlineStatus = new UserOnlineStatus();
        Role role = roleService.getByName("USER").getData();

        User user = modelMapper.map(userAddDTO, User.class);
        user.setRole(role);
        user.setUserStatus(UserStatus.PASSIVE);
        user.setPassword(passwordEncryptor.encrypt(userAddDTO.getPassword()));
        user.setUserOnlineStatus(userOnlineStatus);

        userRepository.save(user);

        return new SuccessDataResult<>(user, "User %s added".formatted(user.getUsername()));
    }

    @Override
    @Transactional
    public Result update(UserUpdateDTO userUpdateDTO) {

        // Test for username availability
        Optional<User> usernameTest = userRepository.findByUsername(userUpdateDTO.getUsername());
        if (usernameTest.isPresent() && !usernameTest.get().getId().equals(userUpdateDTO.getId())) {
            return new ErrorResult("Username %s is taken".formatted(userUpdateDTO.getUsername()));
        }

        // Get existing user
        User user = userRepository.findById(userUpdateDTO.getId()).orElseThrow(NotFoundException::new);

        user.setUsername(userUpdateDTO.getUsername());

        // Update password only if new password presents
        if(Objects.nonNull(userUpdateDTO.getPassword())) {
            user.setPassword(passwordEncryptor.encrypt(userUpdateDTO.getPassword()));
        }

        userRepository.save(user);

        return new SuccessResult("User %s updated".formatted(user.getUsername()));
    }

    @Override
    @Transactional
    public Result delete(UserDeleteDTO userDeleteDTO) {
        User user = userRepository.findById(userDeleteDTO.getId()).orElseThrow(NotFoundException::new);
        String username = user.getUsername();
        userRepository.delete(user);
        return new SuccessResult("User %s deleted".formatted(username));
    }

}
