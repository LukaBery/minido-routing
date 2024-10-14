package com.nhnacademy.account.user.dto;

import com.nhnacademy.account.user.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@AllArgsConstructor
public class LoginDto {
    private String id;
    private String pwd;
    private Status status; // 상태확인하려고 임시로 넣은거
}