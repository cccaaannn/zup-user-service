package com.can.zupuserservice.repository;

import com.can.zupuserservice.data.dto.user.UserDTO;
import com.can.zupuserservice.data.entity.UserFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFriendRepository extends JpaRepository<UserFriend, Long> {

    @Query("SELECT " +
            "new com.can.zupuserservice.data.dto.user.UserDTO(" +
            "uf.friendUser.id, " +
            "uf.friendUser.username, " +
            "uf.friendUser.email, " +
            "uf.friendUser.userStatus, " +
            "uf.friendUser.role, " +
            "uf.friendUser.userOnlineStatus, " +
            "true" +
            ") " +
            "FROM UserFriend uf WHERE uf.ownUser.id = :userId ORDER BY uf.friendUser.username ASC")
    List<UserDTO> getAllFriends(Long userId);

    @Query("FROM UserFriend uf WHERE uf.ownUser.id=:ownUserId AND uf.friendUser.id=:friendUserId")
    Optional<UserFriend> getFriend(Long ownUserId, Long friendUserId);

}
