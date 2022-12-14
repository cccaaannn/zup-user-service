package com.kurtcan.zupuserservice.repository;

import com.kurtcan.zupuserservice.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("FROM User u WHERE u.id IN :ids")
    Page<User> findByIds(@Param("ids") List<Long> ids, PageRequest pageRequest);

    @Query("FROM User WHERE username=:username")
    Optional<User> findByUsername(@Param("username") String username);

    @Query("FROM User WHERE id=:id AND username=:username")
    Optional<User> findByUsernameAndId(@Param("id") Long id, @Param("username") String username);

    @Query("FROM User WHERE email=:email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("FROM User WHERE id=:id AND email=:email")
    Optional<User> findByEmailAndId(@Param("id") Long id, @Param("email") String email);

}
