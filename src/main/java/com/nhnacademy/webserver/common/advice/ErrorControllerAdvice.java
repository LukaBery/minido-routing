package com.nhnacademy.webserver.common.advice;

import com.nhnacademy.webserver.common.exception.RestTemplateException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorControllerAdvice {
    @ExceptionHandler(RestTemplateException.class)
    public ModelAndView handleRestTemplateException(RestTemplateException ex) {
        ModelAndView mav = new ModelAndView("error/error"); // error.html이라는 뷰로 리턴
        mav.addObject("status", ex.getStatusCode());      // 에러 상태 코드
        mav.addObject("path", ex.getPath());            // 에러가 발생한 URI
        mav.addObject("message", ex.getMessage());  // 에러 메시지

        return mav;
    }
}
