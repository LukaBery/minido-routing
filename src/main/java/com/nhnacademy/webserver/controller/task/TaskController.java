package com.nhnacademy.webserver.controller.task;

import com.nhnacademy.webserver.common.utils.loginUtil.CustomUserDetail;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class TaskController {
    @GetMapping("/tasks/{id}")
    public ModelAndView getTaskDetail(@PathVariable("id") String id, Authentication authentication){
        ModelAndView mav = new ModelAndView("task/index");
        // TODO:: resttemmplate 데이터 받고 task detail 넣기.
        mav.addObject("userId", ((CustomUserDetail) authentication.getPrincipal()).getUsername());
        return mav;
    }

    @GetMapping("/tasks/project/{id}/new")
    public ModelAndView createNewTaskForm(@PathVariable("id") String id, Authentication authentication){
        ModelAndView mav = new ModelAndView("task/form");
        mav.addObject("projectId", id);
        mav.addObject("userId", ((CustomUserDetail) authentication.getPrincipal()).getUsername());
        return mav;
    }

    @PostMapping("/tasks")
    public void postNewTask(HttpServletResponse res) throws IOException {

        // TODO:: rest template으로 아이디 받아오기.
        String id = null;
        res.sendRedirect("/task/" + id);
    }
}
