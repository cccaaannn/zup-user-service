package com.kurtcan.zupuserservice.service.concretes;

import com.kurtcan.zupuserservice.data.enums.UserStatus;
import com.kurtcan.zupuserservice.core.exception.ForbiddenException;
import com.kurtcan.zupuserservice.core.exception.NotFoundException;
import com.kurtcan.zupuserservice.core.security.encryption.abstracts.IPasswordEncryptor;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.DataResult;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.Result;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.ErrorDataResult;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.ErrorResult;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.SuccessDataResult;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.SuccessResult;
import com.kurtcan.zupuserservice.data.dto.TokenPayload;
import com.kurtcan.zupuserservice.data.dto.user.UserAddDTO;
import com.kurtcan.zupuserservice.data.dto.user.UserDTO;
import com.kurtcan.zupuserservice.data.dto.user.UserDeleteDTO;
import com.kurtcan.zupuserservice.data.dto.user.UserUpdateDTO;
import com.kurtcan.zupuserservice.data.entity.Role;
import com.kurtcan.zupuserservice.data.entity.User;
import com.kurtcan.zupuserservice.data.entity.UserFriend;
import com.kurtcan.zupuserservice.data.entity.UserOnlineStatus;
import com.kurtcan.zupuserservice.data.enums.DefaultRoles;
import com.kurtcan.zupuserservice.mapper.UserMapper;
import com.kurtcan.zupuserservice.repository.UserRepository;
import com.kurtcan.zupuserservice.service.abstracts.IRoleService;
import com.kurtcan.zupuserservice.service.abstracts.IUserFriendService;
import com.kurtcan.zupuserservice.service.abstracts.IUserService;
import com.kurtcan.zupuserservice.util.MessageUtils;
import com.kurtcan.zupuserservice.util.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final IRoleService roleService;
    private final IUserFriendService userFriendService;
    private final IPasswordEncryptor passwordEncryptor;
    private final TokenUtils tokenUtils;
    private final UserMapper userMapper;
    private final MessageUtils messageUtils;

    @Autowired
    public UserService(
            UserRepository userRepository,
            IRoleService roleService,
            IUserFriendService userFriendService,
            IPasswordEncryptor passwordEncryptor,
            TokenUtils tokenUtils,
            UserMapper userMapper,
            MessageUtils messageUtils
    ) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userFriendService = userFriendService;
        this.passwordEncryptor = passwordEncryptor;
        this.tokenUtils = tokenUtils;
        this.userMapper = userMapper;
        this.messageUtils = messageUtils;
    }

    @Override
    public DataResult<Page<UserDTO>> getAll(PageRequest pageRequest, List<Long> ids) {
        Page<User> usersPage;
        if (ids.isEmpty()) {
            usersPage = userRepository.findAll(pageRequest);
        } else {
            usersPage = userRepository.findByIds(ids, pageRequest);
        }

        return new SuccessDataResult<>(new PageImpl<>(userMapper.usersToUserDTOs(usersPage.getContent()), pageRequest, usersPage.getTotalElements()));
    }

    @Override
    public DataResult<UserDTO> getById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        UserDTO userDTO = userMapper.userToUserDTO(user);
        userDTO.setEmail(null);

        TokenPayload tokenPayload = tokenUtils.getTokenPayload();
        UserFriend userFriend = userFriendService.getFriend(tokenPayload.getId(), userId).getData().orElse(null);
        userDTO.setIsFriend(Objects.nonNull(userFriend));

        return new SuccessDataResult<>(userDTO);
    }

    @Override
    public DataResult<User> getByIdInternal(Long userId) {
        return new SuccessDataResult<>(userRepository.findById(userId).orElseThrow(NotFoundException::new));
    }

    @Override
    public DataResult<UserDTO> getByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(NotFoundException::new);
        UserDTO userDTO = userMapper.userToUserDTO(user);
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
    public Result selfActivateUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        user.setUserStatus(UserStatus.ACTIVE);
        userRepository.save(user);

        log.info("User {} changed password, username: {}", id, user.getUsername());
        return new SuccessResult(messageUtils.getMessage("user.success.activated"));
    }

    @Override
    @Transactional
    public Result activateUser(Long id) {
        if (!canChangeStatus(id)) {
            throw new ForbiddenException();
        }
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        user.setUserStatus(UserStatus.ACTIVE);
        userRepository.save(user);

        log.info("User {} changed password, username: {}", id, user.getUsername());
        return new SuccessResult(messageUtils.getMessage("user.success.activated"));
    }

    @Override
    @Transactional
    public Result suspendUser(Long id) {
        if (!canChangeStatus(id)) {
            throw new ForbiddenException();
        }
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        user.setUserStatus(UserStatus.SUSPENDED);
        userRepository.save(user);

        log.info("User {} changed password, username: {}", id, user.getUsername());
        return new SuccessResult(messageUtils.getMessage("user.success.suspended"));
    }

    @Override
    @Transactional
    public Result changePassword(Long id, String newPassword) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        String encryptedPassword = passwordEncryptor.encrypt(newPassword);
        user.setPassword(encryptedPassword);
        userRepository.save(user);

        log.info("User {} changed password, username: {}", id, user.getUsername());
        return new SuccessResult(messageUtils.getMessage("user.success.password-changed"));
    }

    @Override
    @Transactional
    public DataResult<User> add(UserAddDTO userAddDTO) {

//        if (isExistsByUsername(userAddDTO.getUsername())) {
//            return new ErrorResult("Username %s is taken".formatted(userAddDTO.getUsername()));
//        }
        if (isExistsByEmail(userAddDTO.getEmail())) {
            return new ErrorDataResult<>(messageUtils.getMessage("user.error.email-taken", userAddDTO.getEmail()));
        }

        UserOnlineStatus userOnlineStatus = new UserOnlineStatus();
        Role role = roleService.getByName("USER").getData();

        User user = userMapper.userAddDTOToUser(userAddDTO);
        user.setRole(role);
        user.setUserStatus(UserStatus.PASSIVE);
        user.setPassword(passwordEncryptor.encrypt(userAddDTO.getPassword()));
        user.setUserOnlineStatus(userOnlineStatus);

        userRepository.save(user);

        log.info("User {} created, username: {}", user.getId(), user.getUsername());
        return new SuccessDataResult<>(user, messageUtils.getMessage("user.success.added", user.getUsername()));
    }

    @Override
    @Transactional
    public Result update(UserUpdateDTO userUpdateDTO) {

        // Test for username availability
        Optional<User> usernameTest = userRepository.findByUsername(userUpdateDTO.getUsername());
        if (usernameTest.isPresent() && !usernameTest.get().getId().equals(userUpdateDTO.getId())) {
            return new ErrorResult(messageUtils.getMessage("user.error.username-taken", userUpdateDTO.getUsername()));
        }

        // Get existing user
        User user = userRepository.findById(userUpdateDTO.getId()).orElseThrow(NotFoundException::new);

        user.setUsername(userUpdateDTO.getUsername());

        // Update password only if new password presents
        if (Objects.nonNull(userUpdateDTO.getPassword())) {
            user.setPassword(passwordEncryptor.encrypt(userUpdateDTO.getPassword()));
        }

        userRepository.save(user);

        log.info("User {} updated, username: {}", user.getId(), user.getUsername());
        return new SuccessResult(messageUtils.getMessage("user.success.updated", user.getUsername()));
    }

    @Override
    @Transactional
    public Result delete(UserDeleteDTO userDeleteDTO) {
        User user = userRepository.findById(userDeleteDTO.getId()).orElseThrow(NotFoundException::new);
        String username = user.getUsername();
        userRepository.delete(user);

        log.info("User {} deleted, username: {}", user.getId(), user.getUsername());
        return new SuccessResult(messageUtils.getMessage("user.success.deleted", username));
    }


    private boolean canChangeStatus(Long id) {
        TokenPayload tokenPayload = tokenUtils.getTokenPayload();
        if (
                tokenPayload.getRole().equals(DefaultRoles.ADMIN.name) ||
                        tokenPayload.getRole().equals(DefaultRoles.SYS_ADMIN.name) ||
                        tokenPayload.getId().equals(id)
        ) {
            return true;
        }
        return false;
    }

}
