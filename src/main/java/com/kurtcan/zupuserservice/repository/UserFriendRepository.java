package com.kurtcan.zupuserservice.repository;

import com.kurtcan.zupuserservice.data.entity.User;
import com.kurtcan.zupuserservice.data.entity.UserFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFriendRepository extends JpaRepository<UserFriend, Long> {

    @Query("SELECT uf.friendUser FROM UserFriend uf WHERE uf.ownUser.id = :userId ORDER BY uf.friendUser.username ASC")
    List<User> getFriends(@Param("userId") Long userId);

    @Query("FROM UserFriend uf WHERE uf.ownUser.id=:ownUserId AND uf.friendUser.id=:friendUserId")
    Optional<UserFriend> getFriend(@Param("ownUserId") Long ownUserId, @Param("friendUserId") Long friendUserId);

}
