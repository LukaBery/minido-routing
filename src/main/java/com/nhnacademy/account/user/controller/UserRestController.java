package com.nhnacademy.account.user.controller;

import com.nhnacademy.account.user.dto.UserDto;
import com.nhnacademy.account.user.dto.UserRegisterDto;
import com.nhnacademy.account.user.dto.UserUpdateDto;
import com.nhnacademy.account.user.entity.Status;
import com.nhnacademy.account.user.entity.User;
import com.nhnacademy.account.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;


    //유저 전체 조회
    @GetMapping("/users") //조회할 때도 status 상태에 따라 나타내야함
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = userService.getAllByStatus(Status.JOIN);
        return ResponseEntity.ok(users); // 비어있으면 빈 리스트
    }

    //유저 하나만 검색해서 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") String userId) {
        UserDto user = userService.getUserByIdAndStatus(userId, Status.JOIN);
        return ResponseEntity.ok().body(user);
    }

    //진짜 삭제하는 게 아니라 상태변경하는 버전
    //RequestBody에 status를 받아서 처리해야함 api를 하나만 해야할듯
    @DeleteMapping("/user/{userId}/withdrawal")
    public ResponseEntity<Void> withdrawalUser(@PathVariable("userId") String userId) {
        try {
            userService.WithdrawalUser(userId);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            log.error("Error deactivating user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //휴먼 상태
    @PatchMapping("/user/{userId}/dormant")
    public ResponseEntity<Void> DormantUser(@PathVariable("userId") String userId) {
        try {
            userService.DormantUser(userId);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            log.error("Error deactivating user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 유저 생성
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        User user = userService.createAccount(userRegisterDto);
        return ResponseEntity.ok(user);
    }


    // 유저 수정
    @PutMapping("/user/update")
    public ResponseEntity<User> updateUser(@RequestBody UserUpdateDto userUpdateDto) {
        User updatedUser = userService.updateUserById(userUpdateDto);
        return ResponseEntity.ok(updatedUser);
    }
}
