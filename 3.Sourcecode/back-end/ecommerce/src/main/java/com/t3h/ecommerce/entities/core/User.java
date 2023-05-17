package com.t3h.ecommerce.entities.core;


import com.t3h.ecommerce.entities.BaseEntity;
import com.t3h.ecommerce.entities.order.Orders;
import com.t3h.ecommerce.entities.product.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "status",columnDefinition = "integer default 1")
    private Integer status;

    @Column(name = "email")
    private String email;

    @Column(name = "avatar", columnDefinition = "text")
    private String avatar;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "address")
    private String address;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public User(Long createdDate, Long updatedDate,String username, String password, String email, String fullName, String avatar,
                Integer gender, String phoneNumber, String address) {
        super(createdDate, updatedDate);
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.avatar = avatar;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Orders> orders;

}
