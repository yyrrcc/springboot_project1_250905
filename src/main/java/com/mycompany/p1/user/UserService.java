package com.mycompany.p1.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// 회원가입 + 비밀번호 암호화
	public SiteUser create(String username, String password, String email) {
		SiteUser user = new SiteUser();
		user.setUsername(username);
		
		// 비밀번호 암호화 (BCryptPasswordEncoder : 스프링 시큐리티 모듈에서 제공하는 암호화 도구)
		//BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String crtptPassword = passwordEncoder.encode(password);
		user.setPassword(crtptPassword);
		
		user.setEmail(email);
		userRepository.save(user);
		return user;
		
	}

}
