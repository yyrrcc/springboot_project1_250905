package com.mycompany.p1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mycompany.p1.question.QuestionService;

@SpringBootTest
public class DummyDataInputTest {
	
	@Autowired
	private QuestionService questionService;
	
	@Test
	@DisplayName("300개의 질문 더미 데이터 생성")
	public void testDummy() {
		for (int i = 1; i <= 300; i++) {
			// 십진수 d를 찍고 03은 3자리를 채워라
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "연습 더미 데이터";
			questionService.create(subject, content, null);	
		}
		
	}

}
