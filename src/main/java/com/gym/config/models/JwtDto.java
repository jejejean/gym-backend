package com.gym.config.models;

import com.gym.user.models.response.UserResponse;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class JwtDto {
    private UserResponse userPrincipal;
    private String bearer = "Bearer";
    private String token;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtDto(UserResponse userPrincipal, String token, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.userPrincipal = userPrincipal;
        this.authorities = authorities;
    }
}
