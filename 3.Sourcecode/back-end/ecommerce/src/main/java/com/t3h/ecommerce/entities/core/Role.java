package com.t3h.ecommerce.entities.core;

import com.t3h.ecommerce.entities.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "role")
public class Role extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;


    @ManyToMany( mappedBy = "roles",fetch = FetchType.LAZY)
    private List<User> user;
}
