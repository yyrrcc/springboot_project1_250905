package com.mycompany.p1.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mycompany.p1.question.DataNotFoundException;

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
	
	
	// 유저 아이디로 유저 정보(엔티티) 가져오기
	public SiteUser getUser(String username) {
		Optional<SiteUser> optional = userRepository.findByUsername(username);
		if (optional.isPresent()) {
			SiteUser siteUser = optional.get();
			return siteUser;
		} else {
			throw new DataNotFoundException("해당 유저를 찾을 수 없습니다.");
		}
	}
	

}
