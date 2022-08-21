package com.can.zupuserservice.repository;

import com.can.zupuserservice.data.entity.UserFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFriendRepository extends JpaRepository<UserFriend, Long> {

}
