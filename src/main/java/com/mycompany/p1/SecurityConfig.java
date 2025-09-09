package com.mycompany.p1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// AntPathRequestMatcher에 취소선이 뜨는 이유는 이 클래스가 Spring Security 6부터 deprecated(사용 중단, 곧 제거 예정) 되었기 때문
// 새로운 코드로 변경해야함

@Configuration // 환경 설정 파일
@EnableWebSecurity // 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 애너테이션
public class SecurityConfig {

	@Bean // 스프링에 의해 생성 또는 관리되는 객체
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// 인증되지 않은 모든 페이지의 요청을 허락한다는 의미 (로그인하지 않더라도 모든 페이지에 접근 가능)
		http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests.requestMatchers("/**").permitAll())
//		.csrf((csrf) -> csrf
//                .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))).headers((headers) -> headers
//                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
//                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
		.formLogin((formLogin) -> formLogin // 스프링 시큐리티에서 로그인 설정
				.loginPage("/user/login") // 로그인 요청
				.defaultSuccessUrl("/")) // 로그인 성공 시 이동 할 페이지 루트로 지정
		.logout((logout) -> logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) // 로그아웃 요청
                .logoutSuccessUrl("/") // 로그아웃 성공 시 이동 할 페이지
                .invalidateHttpSession(true)) // 사용자 세션 삭제
		;
		return http.build();
	}
	
	
	
	@Bean // PasswordEncoder는 BCryptPasswordEncoder의 인터페이스
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	
	
	
	
	@Bean
	// 스프링 시큐리티의 인증을 처리
	AuthenticationManager authenticationManager
	(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
}
