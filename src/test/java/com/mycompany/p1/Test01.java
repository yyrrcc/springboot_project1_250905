package com.mycompany.p1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mycompany.p1.question.Question;
import com.mycompany.p1.question.QuestionRepository;

@SpringBootTest
public class Test01 {
	
	@Autowired
	private QuestionRepository questionRepository;
	
//	@Test
//	@DisplayName("Question 엔티티, 질문 글 넣기")
//	public void testJpa1() {
//		// q1 entity 생성
//		Question q1 = new Question();
//		q1.setSubject("sbb가 무엇인가요?");
//		q1.setContent("sbb에 대해 알고 싶습니다.");
//		q1.setCreatedate(LocalDateTime.now()); // 백엔드 서버 위치의 현재 시간
//		// repos에 저장
//		questionRepository.save(q1);
//		
//		// q1 entity 생성
//		Question q2 = new Question();
//		q2.setSubject("스프링부트 모델이 무엇인가요?");
//		q2.setContent("id는 자동생성 되는 게 맞나요?");
//		q2.setCreatedate(LocalDateTime.now()); // 백엔드 서버 위치의 현재 시간
//		// repos에 저장
//		questionRepository.save(q2);
//	}
	
	
	@Test
	@DisplayName("모든 질문글 조회하기")
	void testJpa2() {
		List<Question> questions = questionRepository.findAll();
		assertEquals(2, questions.size()); // (예상하는 결과, 실제 결과) 값 넣고 확인하는 메서드
		Question question = questions.get(0); // 첫번째 질문글 가져오기
		assertEquals("sbb가 무엇인가요?", question.getSubject());
		
	}
	
	
	
	@Test
	@DisplayName("글 번호로 조회하기")
	void testJpa3() {
		Optional<Question> questionOptional = questionRepository.findById(1);
		if (questionOptional.isPresent()) {
			Question question = questionOptional.get(); // 글 꺼내기
			assertEquals("sbb가 무엇인가요?", question.getSubject());
		}
	}
	
	
	
	
	@Test
	@DisplayName("질문 제목으로 조회")
	void testJpa4() {
		Question question = questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(1, question.getId());
	}
	
	
	
	@Test
	@DisplayName("질문 제목과 내용으로 조회")
	void testJpa5() {
		Question question = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해 알고 싶습니다.");
		assertEquals(1, question.getId());
	}
	
	
	
	
	@Test
	@DisplayName("특정 문자열을 포함한 제목 찾기")
	void testJpa6() {
		List<Question> questionList = questionRepository.findBySubjectLike("sbb%");
		Question question = questionList.get(0); // 첫번째 레코드 가져오기
		assertEquals("sbb가 무엇인가요?", question.getSubject());
	}

}
