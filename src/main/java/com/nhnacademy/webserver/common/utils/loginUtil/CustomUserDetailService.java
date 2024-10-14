package com.nhnacademy.webserver.common.utils.loginUtil;

import com.nhnacademy.webserver.dto.auth.LoginResponseDto;
import com.nhnacademy.webserver.enums.API;
import com.nhnacademy.webserver.common.utils.routeUtil.RestTemplateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final RestTemplateUtil restTemplateUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ResponseEntity<LoginResponseDto> re = restTemplateUtil.getResponse(username, API.ACCOUNT, "/login/" + username, LoginResponseDto.class);
        LoginResponseDto loginResponseDto = re.getBody();

        return new CustomUserDetail(loginResponseDto);
    }
}
