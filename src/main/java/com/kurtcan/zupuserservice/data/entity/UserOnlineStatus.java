package com.kurtcan.zupuserservice.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kurtcan.javacore.data.enums.OnlineStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

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
    @Column(name = "last_online", nullable = false)
    private OffsetDateTime lastOnline;

    @ToString.Exclude
    @JsonIgnore
    @OneToOne(mappedBy = "userOnlineStatus", fetch = FetchType.LAZY)
    private User user;

}
