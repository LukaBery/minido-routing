package com.nhnacademy.webserver.controller.user;

import com.nhnacademy.webserver.dto.auth.SignOnRequestDto;
import com.nhnacademy.webserver.dto.user.UserDto;
import com.nhnacademy.webserver.service.user.UserService;
import com.nhnacademy.webserver.common.utils.loginUtil.CookieUtil;
import com.nhnacademy.webserver.common.utils.loginUtil.CustomUserDetail;
import com.nhnacademy.webserver.common.utils.loginUtil.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final SessionService sessionService;

    @GetMapping("/user")
    public ModelAndView getUserDetail(Authentication authentication){
        String id = ((CustomUserDetail) authentication.getPrincipal()).getUsername();

        UserDto userDto = userService.getUser(id);

        ModelAndView mav = new ModelAndView("user/index");

        mav.addObject("user", userDto);

        return mav;
    }

    @PostMapping("/user")
    public void createUser(@ModelAttribute SignOnRequestDto signOnRequestDto, HttpServletRequest req, HttpServletResponse res) throws IOException {
        UserDto userDto = userService.createUser(req, signOnRequestDto);

        String sessionId = sessionService.saveUser(userDto.getId());

        CookieUtil.create(sessionId, res);

        res.sendRedirect("/");
    }

    @PutMapping("/user/update")
    public void updateUser(@ModelAttribute UserDto userDto, HttpServletRequest req, HttpServletResponse res) throws IOException {
        userService.updateUser(req, userDto);
        res.sendRedirect("/");
    }

    @DeleteMapping("/user/{id}/withdrawal")
    public void deleteUser(HttpServletRequest req, HttpServletResponse res) throws IOException {
        userService.deleteUser(req);

        CookieUtil.remove(res);

        res.sendRedirect("/login");
    }
}
