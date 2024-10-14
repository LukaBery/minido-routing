package com.nhnacademy.account.user.service.impl;

import com.nhnacademy.account.exception.AccountNotFoundException;
import com.nhnacademy.account.exception.UserAlreadyExistException;
import com.nhnacademy.account.user.dto.LoginDto;
import com.nhnacademy.account.user.dto.UserDto;
import com.nhnacademy.account.user.dto.UserRegisterDto;
import com.nhnacademy.account.user.dto.UserUpdateDto;
import com.nhnacademy.account.user.entity.Status;
import com.nhnacademy.account.user.entity.User;
import com.nhnacademy.account.user.repository.UserRepository;
import com.nhnacademy.account.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createAccount(UserRegisterDto userRegisterDto) {
        User user = new User();
        BeanUtils.copyProperties(userRegisterDto,user);
        user.setStatus(Status.JOIN);
        user.setCreatedAt(LocalDateTime.now().withNano(0));
        if(userRepository.existsById(user.getId())) {
            throw new UserAlreadyExistException("유저ID가 중복입니다");
        }
        return userRepository.save(user);
    }

    @Override
    public UserDto getUser(String userId) {
        if(!userRepository.existsById(userId)) {
            throw new RuntimeException("찾으시는 회원이 존재하지 않습니다");
        }

        User user = userRepository.findById(userId).orElseThrow();
        UserDto userDto = new UserDto(user.getId(),user.getEmail());
        return userDto;
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> userList = userRepository.findAll();

        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            userDtoList.add(new UserDto(user.getId(), user.getEmail()));
        }
        return userDtoList;
    }

    @Override
    public LoginDto getLoginInfoByStatus(String userId,Status status) {
        User user = userRepository.findByIdAndStatus(userId,status).orElseThrow();
        return new LoginDto(user.getId(), user.getPwd(),user.getStatus());
    }

    //탈퇴회원을 상태변경으로 처리 Status 0:회원가입 1:탈퇴 2:휴먼
    @Override
    public LoginDto WithdrawalUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User not found: " + id));
        user.setStatus(Status.WITHDRAWAL); // 상태를 탈퇴로 변경
        userRepository.save(user);

        return new LoginDto(user.getId(),user.getPwd(),user.getStatus());
        }

    @Override
    public LoginDto DormantUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User not found: " + id));
        user.setStatus(Status.DORMANT); // 상태를 탈퇴로 변경
        userRepository.save(user);

        return new LoginDto(user.getId(),user.getPwd(),user.getStatus());
    }

    @Override
    public List<UserDto> getAllByStatus(Status status) {
        List<User> users = userRepository.findALLByStatus(status);
        return users.stream()
                .map(user -> new UserDto(user.getId(), user.getEmail())) // User -> UserDto 변환
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserByIdAndStatus(String userId, Status status) {
        User user = userRepository.findByIdAndStatus(userId, status)
                .orElseThrow(() -> new AccountNotFoundException("찾으시는 유저가 존재하지 않거나 탈퇴 처리되었습니다."));
        return new UserDto(user.getId(), user.getEmail()); // User 엔티티를 DTO로 변환하는 로직
    }

    @Override
    public LoginDto getUserByStatus(String userId, Status status) {
        User user = userRepository.findByIdAndStatus(userId, status)
                .orElseThrow(() -> new AccountNotFoundException("찾으시는 유저가 존재하지 않거나 탈퇴 처리되었습니다."));
        return new LoginDto(user.getId(), user.getPwd(), user.getStatus()); // User 엔티티를 DTO로 변환하는 로직
    }

    @Override
    public User updateUserById(UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(userUpdateDto.getId())
                .orElseThrow(() -> new AccountNotFoundException("찾으시는 유저가 존재하지 않슴둥"));
        user.setEmail(userUpdateDto.getEmail());

        return userRepository.save(user);
    }





//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new AccountNotFoundException("찾으시는 유저가 존재하지 않슴둥"));
//
//        user.setPwd(user.getPwd());
//        user.setEmail(user.getEmail());
//
//        return userRepository.save(user);
//    }
}

