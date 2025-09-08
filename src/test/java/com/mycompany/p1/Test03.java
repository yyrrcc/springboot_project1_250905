package com.mycompany.p1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.p1.answer.Answer;
import com.mycompany.p1.answer.AnswerRepository;
import com.mycompany.p1.question.Question;
import com.mycompany.p1.question.QuestionRepository;

@SpringBootTest
public class Test03 {
	
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;
	
	
//	@Test
//	@DisplayName("답변 게시판 글 생성하기 (질문글 번호 알아야 함)")
//	void testJpa1() {
//		// 기본키로 질문글이 존재하는지 확인 후 가져온다. 그리고 setter 이용해서 넣어줌
//		Optional<Question> optional = questionRepository.findById(2);
//		assertTrue(optional.isPresent());
//		Question question = optional.get();
//		
//		Answer answer = new Answer();
//		answer.setContent("네, 자동으로 생성됩니다.");
//		answer.setCreatedate(LocalDateTime.now());
//		answer.setQuestion(question); // 질문글 번호
//		answerRepository.save(answer);
//	}
	
	
	
	
	
	@Test
	@DisplayName("답변 게시판 글 조회")
	void testJpa2() {
		Optional<Answer> optional = answerRepository.findById(1);
		assertTrue(optional.isPresent()); // 기본키로 가져온 답변글이 존재하는 지 여부
		Answer answer = optional.get();
		assertEquals(2, answer.getQuestion().getId()); // 질문글의 아이디를 가져와서 확인하기
	}
	
	

	
	@Test
	@DisplayName("질문글에 있는 모든 답변 조회")
	@Transactional // 게으른 초기화 문제 해결 : 메서드가 종료될 때까지 DB 커넥션이 유지됨
	void testJpa3() {
		Optional<Question> optional = questionRepository.findById(2);
		assertTrue(optional.isPresent());
		Question question = optional.get(); // findById 후 DB 세션이 끊어짐!
		List<Answer> answerList = question.getAnswerList(); // 해당 질문글에 대한 답변 리스트
		assertEquals(1, answerList.size());
		assertEquals("네, 자동으로 생성됩니다.", answerList.get(0).getContent());
	}
	

}
