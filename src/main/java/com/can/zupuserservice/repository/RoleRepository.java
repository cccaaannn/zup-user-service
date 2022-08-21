package com.can.zupuserservice.repository;

import com.can.zupuserservice.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("FROM Role WHERE name = :name")
    Optional<Role> findByName(@Param("name") String name);

}
