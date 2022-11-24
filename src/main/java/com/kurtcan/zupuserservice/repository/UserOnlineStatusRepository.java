package com.kurtcan.zupuserservice.repository;

import com.kurtcan.zupuserservice.data.entity.UserOnlineStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
public interface UserOnlineStatusRepository extends JpaRepository<UserOnlineStatus, Long> {

    @Query("FROM UserOnlineStatus WHERE user.id = :userId")
    Optional<UserOnlineStatus> findByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
//    @Query("UPDATE UserOnlineStatus SET onlineStatus = :newStatus WHERE user.id = :userId")
//    @Query(value = "UPDATE user_online_status uos LEFT JOIN user u ON uos.id=u.user_online_status SET uos.status = :newStatus WHERE u.id = :userId", nativeQuery = true)
//    @Query("UPDATE UserOnlineStatus SET onlineStatus = :newStatus WHERE id in (SELECT User.userOnlineStatus FROM User WHERE id = :userId)")
    @Query(value = "UPDATE user_online_status SET status = :newStatus, last_online = :lastOnline WHERE id in (SELECT user.user_online_status FROM user WHERE id = :userId)", nativeQuery = true)
    void updateUserOnlineStatus(@Param("userId") Long userId, @Param("newStatus") Integer newStatus, @Param("lastOnline") OffsetDateTime lastOnline);

}
