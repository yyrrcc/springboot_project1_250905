package com.mycompany.p1.answer;

import java.time.LocalDateTime;
import java.util.Optional;
import com.mycompany.p1.user.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.p1.question.DataNotFoundException;
import com.mycompany.p1.question.Question;
import com.mycompany.p1.user.SiteUser;

@Service
public class AnswerService {

    private final UserController userController;

	@Autowired
	private AnswerRepository answerRepository;


    AnswerService(UserController userController) {
        this.userController = userController;
    }
	
	
	// 답변 저장하기 (질문글 번호, 답변내용)
	public Answer create(Question question, String content, SiteUser author) {
		Answer answer = new Answer();
		answer.setQuestion(question);
		answer.setContent(content);
		answer.setCreatedate(LocalDateTime.now());
		answer.setAuthor(author); // 글쓴이 추가
		answerRepository.save(answer);
		return answer; // 반환타입을 만들어줌
	}
	
	
	// 기본키 이용해서 답변 가져오기
	public Answer getAnswer(Integer id) {
		Optional<Answer> optional = answerRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new DataNotFoundException("해당 답변이 존재하지 않습니다.");
		}
	}
	
	
	// 답변 수정하기
	public void modify(Answer answer, String content) {
		answer.setContent(content);
		answer.setModifydate(LocalDateTime.now());
		answerRepository.save(answer);
	}
	
	// 답변 삭제하기
	public void delete(Answer answer) {
		answerRepository.delete(answer);
	}
	
	
	// 답변 추천
	public void vote(Answer answer, SiteUser siteUser) {
		answer.getVoter().add(siteUser); // 답변글의 voter를 가져와서 siteUser 엔티티에 값을 추가
		answerRepository.save(answer); // 변경된 answer 다시 저장
	}
	
	// 답변 비추
	public void dislike(Answer answer, SiteUser siteUser) {
		answer.getDislikeusers().add(siteUser);
		answerRepository.save(answer);
	}
	
}
