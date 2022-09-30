package com.can.zupuserservice.data.entity;

import com.can.zupuserservice.core.data.enums.OnlineStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_online_status")
public class UserOnlineStatus {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "status", nullable = false)
    private OnlineStatus onlineStatus = OnlineStatus.OFFLINE;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_online", nullable = false)
    private Date lastOnline;

    @ToString.Exclude
    @JsonIgnore
    @OneToOne(mappedBy = "userOnlineStatus", fetch = FetchType.LAZY)
    private User user;

}
