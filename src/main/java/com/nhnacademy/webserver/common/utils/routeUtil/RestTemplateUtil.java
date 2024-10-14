package com.nhnacademy.webserver.common.utils.routeUtil;

import com.nhnacademy.webserver.dto.error.ErrorMessageDto;
import com.nhnacademy.webserver.enums.API;
import com.nhnacademy.webserver.common.exception.RestTemplateException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class RestTemplateUtil {
    private final RestTemplate restTemplate;

    public <T>ResponseEntity<T> getResponse(String userId, API domain, HttpServletRequest req, Object body, Class<T> resClass){
        HttpEntity entity = createEntity(body, userId);
        ResponseEntity<T> re = null;

        try{
            re = restTemplate.exchange(domain + req.getRequestURI(), HttpMethod.valueOf(req.getMethod()), entity, resClass);
        }catch (HttpStatusCodeException e){
            throw new RestTemplateException(e.getStatusCode(), req.getRequestURI(), extractError(e).getMessage());
        }

        return re;
    }

    public <T>ResponseEntity<T> getResponse(String userId, API domain, String uri, Class<T> resClass){
        HttpEntity entity = createEntity(null, userId);
        ResponseEntity<T> re = null;

        try{
            re = restTemplate.exchange(domain + uri, HttpMethod.GET, entity, resClass);
        }catch (HttpStatusCodeException e){
            if(uri.contains("login")) {
                // spring security 로그인 실패 처리
                throw new UsernameNotFoundException("유저를 찾을 수 없습니다.");
            }
            else{
                throw new RestTemplateException(e.getStatusCode(), uri, extractError(e).getMessage());
            }
        }

        return re;

    }

    private HttpEntity createEntity(Object body, String userId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-USER-ID", userId);
        return new HttpEntity(body, headers);
    }

    private ErrorMessageDto extractError(HttpStatusCodeException e){
        return e.getResponseBodyAs(ErrorMessageDto.class);
    }
}
