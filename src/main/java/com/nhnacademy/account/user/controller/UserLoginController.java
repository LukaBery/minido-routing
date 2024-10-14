package com.nhnacademy.account.user.controller;

import com.nhnacademy.account.user.dto.LoginDto;
import com.nhnacademy.account.user.entity.Status;
import com.nhnacademy.account.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// controller: 사용자의 요청에 따라서 응답 넘겨주면됨

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserLoginController {

    private final UserService userService;

    //UserId로 찾아서 LoginDto를 반환하는거
    @Operation(summary = "로그인 정보 가져오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    @GetMapping("/login/{userId}")
    public ResponseEntity<?> getLoginInfo(@PathVariable("userId") String userId) {
            LoginDto loginDto = userService.getUserByStatus(userId, Status.JOIN);
            return ResponseEntity.ok(loginDto);
    }
}
