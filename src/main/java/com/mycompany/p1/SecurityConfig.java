package com.mycompany.p1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 환경 설정 파일
@EnableWebSecurity // 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 애너테이션
public class SecurityConfig {

	@Bean // 스프링에 의해 생성 또는 관리되는 객체
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		// 인증되지 않은 모든 페이지의 요청을 허락한다는 의미 (로그인하지 않더라도 모든 페이지에 접근 가능)
		httpSecurity.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
//		.csrf((csrf) -> csrf
//                .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))).headers((headers) -> headers
//                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
//                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
		;
		return httpSecurity.build();
	}
	
	
	
	@Bean // PasswordEncoder는 BCryptPasswordEncoder의 인터페이스
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
