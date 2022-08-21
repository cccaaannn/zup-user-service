package com.can.zupuserservice.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_friend")
@EqualsAndHashCode(callSuper = true)
public class UserFriend extends EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "own_user_id", referencedColumnName = "id")
    private User ownUser;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "friend_user_id", referencedColumnName = "id")
    private User friendUser;


    public void setOwnUserFromId(Long id) {
        setOwnUser(new User(id));
    }

    public void setFriendUserFromId(Long id) {
        setFriendUser(new User(id));
    }

}
