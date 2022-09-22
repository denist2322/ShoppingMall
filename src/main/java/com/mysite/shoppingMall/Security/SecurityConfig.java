package com.mysite.shoppingMall.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
// == 보안관련 DTO ==
public class SecurityConfig {

    @Bean
    // csrf(정상적인 사용자가 의도치 않은 위조요청을 보내는 것)보안을 사용하지 않고, 모든 경로에서 접근 권한을 허락한다.
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().antMatchers("/**").permitAll();        ;
        return http.build();
    }

    @Bean
    // 비밀번호를 암호화 하기위함.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}