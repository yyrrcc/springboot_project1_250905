package com.mycompany.p1;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mycompany.p1.entity.Question;
import com.mycompany.p1.repository.QuestionRepository;

@SpringBootTest
public class Test01 {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Test
	public void testJpa1() {
		// q1 entity 생성
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now()); // 백엔드 서버 위치의 현재 시간
		// repos에 저장
		questionRepository.save(q1);
		
		// q1 entity 생성
		Question q2 = new Question();
		q2.setSubject("스프링부트 모델이 무엇인가요?");
		q2.setContent("id는 자동생성 되는 게 맞나요?");
		q2.setCreateDate(LocalDateTime.now()); // 백엔드 서버 위치의 현재 시간
		// repos에 저장
		questionRepository.save(q2);
		
	}

}
