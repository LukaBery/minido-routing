package com.nhnacademy.webserver.common.utils.loginUtil;

import com.nhnacademy.webserver.dto.auth.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserDetail implements UserDetails {
    private final LoginResponseDto loginResponseDto;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return loginResponseDto.getPwd();
    }

    @Override
    public String getUsername() {
        return loginResponseDto.getId();
    }
}
