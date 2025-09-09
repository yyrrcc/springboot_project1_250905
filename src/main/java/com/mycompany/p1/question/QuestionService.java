package com.mycompany.p1.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
	
	// @Autowired로 의존성 주입하거나, final 선언 후 생성자 이용해서 초기화 해도 됨
	@Autowired
	private QuestionRepository questionRepository;
	

	// 모든 질문글 가져오기
	public List<Question> getList() {
		return questionRepository.findAll();
	}
	// 모든 질문글 가져오기 + 페이징
//	public Page<Question> getList(int page) {
//		// 매개변수 page (유저가 누른 페이지 넘버) 받아오고, 1페이지 당 보여질 게시글의 수
//		Pageable pageable = PageRequest.of(page, 10);
//		return questionRepository.findAll(pageable);
//	}
	
	// 질문 제목 클릭 시 내용 확인
	public Question getQuestion(Integer id) {
		Optional<Question> optional = questionRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			// 예외 클래스 실행
			throw new DataNotFoundException("question not found");
		}
	}
	
	// 질문 등록
	public void create(String subject, String content) {
		Question question = new Question();
		question.setSubject(subject);
		question.setContent(content);
		question.setCreatedate(LocalDateTime.now());
		questionRepository.save(question);
	}
	

}
 