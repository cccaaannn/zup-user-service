package com.can.zupuserservice.repository;

import com.can.zupuserservice.data.entity.User;
import com.can.zupuserservice.data.dto.user.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT new com.can.zupuserservice.data.dto.user.UserDTO(u.id, u.username, u.email, u.userStatus, u.role, u.userOnlineStatus) FROM User u")
    Page<UserDTO> customFindAll(Pageable pageable);

    @Query("FROM User WHERE username=:username")
    Optional<User> findByUsername(@Param("username") String username);

    @Query("FROM User WHERE id=:id AND username=:username")
    Optional<User> findByUsernameAndId(@Param("id") Long id, @Param("username") String username);

    @Query("FROM User WHERE email=:email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("FROM User WHERE id=:id AND email=:email")
    Optional<User> findByEmailAndId(@Param("id") Long id, @Param("email") String email);

}
