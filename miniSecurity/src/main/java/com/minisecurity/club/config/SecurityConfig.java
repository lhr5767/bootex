package com.minisecurity.club.config;

import com.minisecurity.club.security.handler.ClubLoginSuccessHandler;
import com.minisecurity.club.security.service.ClubUserDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)//어노테이션 기반으로 url에 접근제한
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ClubUserDetailsService userDetailsService;

    //패스워드를 인코딩 부트 2.0부터 반드시 필요함
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ClubLoginSuccessHandler successHandler() {
        return new ClubLoginSuccessHandler(passwordEncoder());
    }

    /*@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication().withUser("user1")
                .password("$2a$10$wI4O2MRmHctFPOZNkCzg3e6orQAjx78zs.T9eGgdDnLhJVwmGkMMq")
                .roles("USER");

    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /*http.authorizeRequests()
                .antMatchers("/sample/all").permitAll()
                .antMatchers("/sample/member").hasRole("USER");*/

        http.formLogin(); //인가 , 인증 문제시 로그인 화면
        http.csrf().disable(); //csrf 토큰 발행 x
        http.logout();

        http.oauth2Login().successHandler(successHandler());

        //7일 동안 자동 로그인(쿠키를 사용하는 방식) , 소셜 로그인은 remember-me 사용 불가
        http.rememberMe().tokenValiditySeconds(60*60*24*7).userDetailsService(userDetailsService);
    }
}
