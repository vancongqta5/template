package com.t3h.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String refreshToken;
    private String type = "Bearer";
    private String name;
    private Long id;
    private String avatar;
    private Collection<? extends GrantedAuthority> roles;

    public JwtResponse(String token, String refreshToken, Long id, String name, Collection<? extends GrantedAuthority> roles, String avatar) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.id = id;
        this.name = name;
        this.roles = roles;
        this.avatar = avatar;
    }
}
