package com.mycompany.p1.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

// 회원가입 유효성 검증을 하기 위해 생성
@Getter @Setter
public class UserCreateForm {
	
	@NotEmpty(message = "사용자ID는 필수 항목입니다.")
	@Size (min = 3, max = 25, message = "사용자 ID의 길이는 3자 이상 25자 이하입니다.")
	private String username;
	
	@NotEmpty(message = "비밀번호는 필수 항목입니다.")
	@Size (min = 3, message = "비밀번호는 최소 3자 이상이어야 합니다.")
	private String password1;
	
	@NotEmpty(message = "비밀번호 확인은 필수 항목입니다.")
	@Size (min = 3, message = "비밀번호는 최소 3자 이상이어야 합니다.")
	private String password2;
	
	@NotEmpty(message = "이메일은 필수 항목입니다.")
	@Email
	private String email;

}
