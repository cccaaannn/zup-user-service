package com.kurtcan.zupuserservice.data.entity;

import com.kurtcan.zupuserservice.data.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
@EqualsAndHashCode(callSuper = true)
public class User extends EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "status", nullable = false)
    private UserStatus userStatus = UserStatus.PASSIVE;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "ownUser", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<UserFriend> userFriends;

    //    @ToString.Exclude
    //    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, optional = false, orphanRemoval = true, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "user_online_status", referencedColumnName = "id")
    private UserOnlineStatus userOnlineStatus;

    public User(Long id) {
        this.id = id;
    }

    public void addToFriend(User newFriendUser) {
        UserFriend userFriend = new UserFriend();
        userFriend.setOwnUser(this);
        userFriend.setFriendUser(newFriendUser);
        this.userFriends.add(userFriend);
    }

}
