package com.mycompany.p1.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
// 시큐리티가 로그인 시 사용할 서비스 (UserDetailsService 인터페이스 구현할 것)
public class UserSecurityService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 유저 아이디로 정보 조회
		Optional<SiteUser> optional = userRepository.findByUsername(username);
		// 만약 유저 아이디가 없다면
		if (optional.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		}
		// 유저 아이디가 존재한다면 get으로 가져오기
		SiteUser siteUser = optional.get();
		
		// 사용자의 권한 정보를 나타내는 GrantedAuthority 객체의 리스트
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        // admin인지 user인지 확인 후 알맞은 권한 주기
        if ("admin".equals(username)) {
        	authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
        	authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        
        // 새로운 User 객체 생성해서 반환하기 (아이디, 비밀번호, 권한 리스트)
        // 스프링 시큐리티는 사용자로부터 입력받은 비밀번호와 일치하는지를 검사하는 기능을 내부에 가지고 있음
		return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
	}
	
	
}
