package com.mycompany.p1.answer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AnswerForm { // 답변 글의 제목과 내용의 유효성 체크를 위한 클래스
	
	@NotEmpty(message = "내용은 필수 항목입니다.")
	@Size(min = 5, message = "내용은 최소 5자 이상이어야 합니다.")
	private String content;

}
