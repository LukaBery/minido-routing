package com.nhnacademy.account.user.service;

import com.nhnacademy.account.user.dto.LoginDto;
import com.nhnacademy.account.user.dto.UserDto;
import com.nhnacademy.account.user.dto.UserRegisterDto;
import com.nhnacademy.account.user.dto.UserUpdateDto;
import com.nhnacademy.account.user.entity.Status;
import com.nhnacademy.account.user.entity.User;

import java.util.List;

// 서비스: 비즈니스 로직,

public interface UserService {


    User createAccount(UserRegisterDto userRegisterDto);


    UserDto getUser(String userId);

    List<UserDto> getUsers();


    LoginDto getLoginInfoByStatus(String userId, Status status);

    LoginDto WithdrawalUser(String id);

    LoginDto DormantUser(String id);

    List<UserDto> getAllByStatus(Status status);

    UserDto getUserByIdAndStatus(String userId, Status status);

    LoginDto getUserByStatus(String userId, Status status);

    User updateUserById(UserUpdateDto userUpdateDto);
}