package com.t3h.ecommerce.security.userprincal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.t3h.ecommerce.entities.core.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPrinciple implements UserDetails {
    private Long id;

    private String username;
    @JsonIgnore
    private String password;

    private String fullName;

    private String email;

    private String avatar;


    private Collection<? extends  GrantedAuthority> roles;


    public static UserPrinciple build(User user){
        // tao authorities
        List<GrantedAuthority> authorities = user.getRoles()
                .stream().map(role -> {
                    return new SimpleGrantedAuthority(role.getRoleName().name());
                })
                .collect(Collectors.toList());

        return new UserPrinciple(
                user.getId(), user.getUsername(),
                user.getPassword(), user.getFullName(),
                user.getEmail(), user.getAvatar(), authorities
        );

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
