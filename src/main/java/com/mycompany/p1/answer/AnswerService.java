package com.mycompany.p1.answer;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.p1.question.Question;
import com.mycompany.p1.user.SiteUser;

@Service
public class AnswerService {

	@Autowired
	private AnswerRepository answerRepository;
	
	
	// 답변 저장하기 (질문글 번호, 답변내용)
	public void create(Question question, String content, SiteUser author) {
		Answer answer = new Answer();
		answer.setQuestion(question);
		answer.setContent(content);
		answer.setCreatedate(LocalDateTime.now());
		answer.setAuthor(author); // 글쓴이 추가
		answerRepository.save(answer);
	}
}
