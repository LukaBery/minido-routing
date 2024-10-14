package com.nhnacademy.webserver.dto.project;

import lombok.Data;

@Data
public class ProjMemReqDto {
    String ownerId;
    long projectId;
    String userId;
}
