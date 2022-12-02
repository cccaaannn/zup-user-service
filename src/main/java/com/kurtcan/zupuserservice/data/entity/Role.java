package com.kurtcan.zupuserservice.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Table(name = "role", uniqueConstraints = { @UniqueConstraint(name = "UniqueName", columnNames = { "name" }) })
@EqualsAndHashCode(callSuper = true)
public class Role extends EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<User> users;

}
