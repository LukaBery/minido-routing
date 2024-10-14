package com.nhnacademy.webserver.service.user;

import com.nhnacademy.webserver.dto.auth.SignOnRequestDto;
import com.nhnacademy.webserver.dto.user.UserDto;
import com.nhnacademy.webserver.dto.user.UserTaskDto;
import com.nhnacademy.webserver.enums.API;
import com.nhnacademy.webserver.common.utils.routeUtil.RestTemplateUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final RestTemplateUtil restTemplateUtil;
    private final PasswordEncoder passwordEncoder;

    public UserDto getUser(String id){
        ResponseEntity<UserDto> re = this.restTemplateUtil.getResponse(id, API.ACCOUNT, "/user/" + id, UserDto.class);

        return re.getBody();
    }

    public UserDto createUser(HttpServletRequest req, SignOnRequestDto signOnRequestDto){
        signOnRequestDto.encode(passwordEncoder);

        ResponseEntity<UserDto> reAcc = this.restTemplateUtil.getResponse(signOnRequestDto.getId(), API.ACCOUNT, req, signOnRequestDto, UserDto.class);

        UserTaskDto userTaskDto = new UserTaskDto();
        userTaskDto.setUserId(signOnRequestDto.getId());
        ResponseEntity<Void> reTask = this.restTemplateUtil.getResponse(signOnRequestDto.getId(), API.TASK, req, userTaskDto, Void.class);
        return reAcc.getBody();
    }

    public void updateUser(HttpServletRequest req, UserDto userDto){
        ResponseEntity<UserDto> re = this.restTemplateUtil.getResponse(userDto.getId(), API.ACCOUNT, req, userDto, UserDto.class);
    }

    public void deleteUser(HttpServletRequest req){
        ResponseEntity<Void> reAcc = this.restTemplateUtil.getResponse(null, API.ACCOUNT, req, null, Void.class);
        ResponseEntity<Void> reTask = this.restTemplateUtil.getResponse(null, API.ACCOUNT, req, null, Void.class);

    }
}
