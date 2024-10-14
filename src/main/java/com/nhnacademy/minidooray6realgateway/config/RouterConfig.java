package com.nhnacademy.minidooray6realgateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
            .route("account_route", r -> r.path("/account/**")
                    .filters(o -> o.rewritePath("/account/(?<remaining>.*)", "/${remaining}"))
                    .uri("http://localhost:8081"))
            .route("task_route", r -> r.path("/task/**")
                    .filters(o -> o.rewritePath("/task/(?<remaining>.*)", "/${remaining}"))
                    .uri("http://localhost:8082"))
            .build();
    }
}