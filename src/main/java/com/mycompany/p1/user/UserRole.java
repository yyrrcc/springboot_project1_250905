package com.mycompany.p1.user;

// 스프링 시큐리티는 사용자 인증 후에 사용자에게 부여할 권한과 관련된 내용이 필요
// 열거형
public enum UserRole {
	// 상수
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");

	// 생성자
	UserRole(String value) {
		this.value = value;
	}
	
	// getter
	public String getValue() {
		return value;
	}

	private String value;
}
