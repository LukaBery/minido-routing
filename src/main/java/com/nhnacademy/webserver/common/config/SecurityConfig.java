package com.nhnacademy.webserver.common.config;

import com.nhnacademy.webserver.common.filter.LoginCheckFilter;
import com.nhnacademy.webserver.common.handler.CustomLoginHandler;
import com.nhnacademy.webserver.common.handler.CustomLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private CustomLoginHandler customLoginHandler;
    @Autowired
    private CustomLogoutHandler customLogoutHandler;
    @Autowired
    private LoginCheckFilter loginCheckFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        http.sessionManagement(sessionManagement -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers("/login/**", "/signon/**", "/user").permitAll()
                        .anyRequest().authenticated()
        );

        http.formLogin((formLogin) ->
                formLogin
                        .loginPage("/login")
                        .usernameParameter("id")
                        .passwordParameter("pwd")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .successHandler(customLoginHandler)
        );


        http.logout((logout)->logout.deleteCookies("SESSION")
                .logoutUrl("/logout")
                .logoutSuccessHandler(customLogoutHandler)
        );

        http.addFilterBefore(loginCheckFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}