package com.mycompany.p1.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class QuestionForm { // 질문 글의 제목과 내용의 유효성 체크를 위한 클래스
	
	// @NotEmpty : 공란으로 들어오면 작동
	// @Size : min, max 이용해서 길이 바이트 확인
	
	
	@NotEmpty(message = "제목은 필수 항목입니다.")
	@Size(max = 50, message = "제목은 최대 50자까지 가능합니다.")
	private String subject;
	
	@NotEmpty(message = "내용은 필수 항목입니다.")
	@Size(min = 3, message = "내용은 최소 3자 이상이어야 합니다.")
	private String content;

}
