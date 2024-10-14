package com.nhnacademy.webserver.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class LoginResponseDto {
    @Setter
    private String id;
    @Setter
    private String pwd;

    public LoginResponseDto(String id){
        this.id = id;
        this.pwd = "";
    }
}
