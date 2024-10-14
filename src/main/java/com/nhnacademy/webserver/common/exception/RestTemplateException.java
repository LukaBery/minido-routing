package com.nhnacademy.webserver.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class RestTemplateException extends RuntimeException{
    private HttpStatusCode statusCode;
    private String path;

    public RestTemplateException(HttpStatusCode statusCode, String path, String message){
        super(message);
        this.statusCode = statusCode;
        this.path = path;
    }
}
