package com.nhnacademy.webserver.common.handler;

import com.nhnacademy.webserver.common.utils.loginUtil.CookieUtil;
import com.nhnacademy.webserver.common.utils.loginUtil.SessionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomLoginHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final SessionService sessionService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String id = authentication.getName().toString();
        String sessionId = sessionService.saveUser(id);

        CookieUtil.create(sessionId, response);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
