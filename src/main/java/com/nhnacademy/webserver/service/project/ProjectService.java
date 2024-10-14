package com.nhnacademy.webserver.service.project;

import com.nhnacademy.webserver.dto.project.ProjMemReqDto;
import com.nhnacademy.webserver.dto.project.ProjectBreifDto;
import com.nhnacademy.webserver.dto.project.ProjectDetailDto;
import com.nhnacademy.webserver.dto.project.ProjectReqDto;
import com.nhnacademy.webserver.enums.API;
import com.nhnacademy.webserver.common.utils.routeUtil.RestTemplateUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final RestTemplateUtil restTemplateUtil;

    public ProjectDetailDto getProject(HttpServletRequest req, String userId){
        return restTemplateUtil.getResponse(userId, API.TASK, req.getRequestURI(), ProjectDetailDto.class).getBody();
    }

    public ProjectDetailDto createProject(HttpServletRequest req, ProjectReqDto projectReqDto, String userId){
        return restTemplateUtil.getResponse(userId, API.TASK, req, projectReqDto, ProjectDetailDto.class).getBody();
    }

    public List<ProjectBreifDto> getProjAdminList(String userId){
        return restTemplateUtil.getResponse(userId, API.TASK, "/projects/user", (Class<List<ProjectBreifDto>>) (Class<?>) List.class).getBody();
    }

    public List<ProjectBreifDto> getProjMemList(String userId){
        return restTemplateUtil.getResponse(userId, API.TASK, "/users/"+userId+"/projects", (Class<List<ProjectBreifDto>>) (Class<?>) List.class).getBody();
    }

    public void addMemInProj(HttpServletRequest req, ProjMemReqDto projMemReqDto, String userId){
        restTemplateUtil.getResponse(userId, API.TASK, req, projMemReqDto, Void.class);
    }
}
