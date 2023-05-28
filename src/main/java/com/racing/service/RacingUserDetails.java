package com.racing.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RacingUserDetails implements UserDetails {
    private String password;
    private String username;
    private String role;
    private List<GrantedAuthority> authorities = new ArrayList<>();

    public RacingUserDetails(String username, String password, String role) {
        this.role = role;
        this.username = username;
        this.password = password;
        GrantedAuthority grantedAuthority = () -> {return this.role;};
        authorities.add(grantedAuthority);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
