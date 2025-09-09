package com.mycompany.p1.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@SequenceGenerator (
		name = "USER_SEQ_GENERATOR", 
		sequenceName = "USER_SEQ",
		initialValue = 1,
		allocationSize = 1
		)
@Getter @Setter
@Table(name = "siteuser")
public class SiteUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GENERATOR")	
	private Long id; // 기본키
	
	@Column(unique = true) // 중복 불가
	private String username;
	
	private String password;
	
	@Column(unique = true) // 중복 불가
	private String email;

}
