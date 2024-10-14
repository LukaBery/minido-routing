package com.nhnacademy.webserver.controller.project;

import com.nhnacademy.webserver.dto.project.ProjMemReqDto;
import com.nhnacademy.webserver.dto.project.ProjectBreifDto;
import com.nhnacademy.webserver.dto.project.ProjectDetailDto;
import com.nhnacademy.webserver.dto.project.ProjectReqDto;
import com.nhnacademy.webserver.dto.tag.TagDto;
import com.nhnacademy.webserver.service.project.ProjectService;
import com.nhnacademy.webserver.service.tag.TagService;
import com.nhnacademy.webserver.common.utils.loginUtil.CustomUserDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final TagService tagService;

    @GetMapping("/")
    public ModelAndView hello(HttpServletRequest req, Authentication authentication){
        String userId = ((CustomUserDetail) authentication.getPrincipal()).getUsername();

        ModelAndView mav = new ModelAndView("home");
//        List<ProjectBreifDto> projectsAdmin = projectService.getProjAdminList(userId);
        List<ProjectBreifDto> projectsMem = projectService.getProjMemList(userId);

//        mav.addObject("projects_admin", projectsAdmin);
        mav.addObject("userId", userId);
        mav.addObject("projects_member", projectsMem);
        return mav;
    }

    @GetMapping("/projects/{id}")
    public ModelAndView projectDetail(@PathVariable("id") long id,
                                      HttpServletRequest req,
                                      Authentication authentication){
        String userId = ((CustomUserDetail) authentication.getPrincipal()).getUsername();

        ModelAndView mav = new ModelAndView("project/index");

        ProjectDetailDto projectDetailDto = projectService.getProject(req, userId);
        List<TagDto> tags = tagService.getTagList(id, userId);

        mav.addObject("tags", tags);
        mav.addObject("userId", userId);
        mav.addObject("project", projectDetailDto);
        return mav;
    }

    @PostMapping("/projects")
    public void createProject(@ModelAttribute ProjectReqDto projectReqDto,
                              HttpServletRequest req,
                              HttpServletResponse res,
                              Authentication authentication) throws IOException {
        String userId = ((CustomUserDetail) authentication.getPrincipal()).getUsername();
        ProjectDetailDto projectDetailDto = projectService.createProject(req, projectReqDto, userId);
        res.sendRedirect("/projects/" + projectDetailDto.getProjectId() );
    }

    @PostMapping("/projects/users")
    public void addMemInProj(@ModelAttribute ProjMemReqDto projMemReqDto,
                              HttpServletRequest req,
                              HttpServletResponse res,
                              Authentication authentication) throws IOException {
        String userId = ((CustomUserDetail) authentication.getPrincipal()).getUsername();
        projectService.addMemInProj(req, projMemReqDto, userId);
        res.sendRedirect("/projects/" + projMemReqDto.getProjectId() );
    }
}
