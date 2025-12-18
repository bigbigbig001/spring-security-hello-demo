package com.code.demo1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.swing.*;

/**
 * @author 贾育权~
 * @version 1.0
 */
@Configuration

//@EnableWebSecurity 启用 Web 安全功能；
@EnableWebSecurity
public class SecurityConfig {
    // 内存用户：test / 123456
    //定义一个内存中的用户（用户名 test，密码 123456）；

    //创建一个“内存中的用户”，不需要数据库。
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
      //  Spring Security 中表示用户身份的核心接口；
        UserDetails user = User.builder()
                .username("test")
                .password("{noop}123456") // {noop} 表示明文（仅演示用！）
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    // 安全过滤链配置  配置安全过滤链（核心！）
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 禁用 CSRF（便于 API 测试）
                /*
                *
                * 定义哪一些路径可以就是需要认证
                * */
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/hello").authenticated()
                        .anyRequest().permitAll()
                )
                /*
                *
                *当你 POST 到 /api/login 时，Spring Security
                * 会自动处理认证
                * */
                .formLogin(form -> form
                        .loginProcessingUrl("/api/login") // 自定义登录提交地址
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .permitAll()
                        //满足这个才会就是进行放行 ；
                );



        return http.build();
    }
}
