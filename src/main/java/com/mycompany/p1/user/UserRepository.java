package com.mycompany.p1.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<SiteUser, Long>{
	
	// 유저 아이디로 조회
	public Optional<SiteUser> findByUsername(String username);
	

}
