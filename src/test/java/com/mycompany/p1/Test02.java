package com.mycompany.p1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.p1.question.Question;
import com.mycompany.p1.question.QuestionRepository;

@SpringBootTest
public class Test02 {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	
	
//	@Test
//	@DisplayName("질문 게시판 제목 수정") // update가 따로 없어서, 기본키로 find 찾고 setter 이용해서 변경 후 save
//	@Transactional // @transactional이 존재하면 this.questionRepository.save(question); 생략 가능
//	void testJpa1() {
//		Optional<Question> questionOptional = questionRepository.findById(1);
//		assertTrue(questionOptional.isPresent()); // 기본키로 가져온 레코드가 존재하면 true로 테스트 통과, 없으면 false 테스트 끝
//		Question question = questionOptional.get();
//		question.setSubject("수정된 제목");
//		this.questionRepository.save(question);
//	}
	
	
	
	
	@Test
	@DisplayName("질문 게시판 글 삭제하기")
	void testJpa2() {
		assertEquals(2, questionRepository.count()); // 모든 행의 개수 반환
		Optional<Question> optional = questionRepository.findById(1); // 기본키로 글 하나 가져오기
		assertTrue(optional.isPresent()); // 글의 존재 여부
		Question question = optional.get();
		questionRepository.delete(question); // 해당 글 삭제
		assertEquals(1, questionRepository.count()); // 삭제 잘 됐는지 개수로 테스트
	}
	
	
	
	
	

}
