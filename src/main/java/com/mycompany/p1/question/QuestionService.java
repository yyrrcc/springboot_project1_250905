package com.mycompany.p1.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mycompany.p1.answer.Answer;
import com.mycompany.p1.user.SiteUser;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import javassist.SerialVersionUID;

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
	public Page<Question> getPageQuestions(int page, String kw) {
		//Specification<Question> spec = search(kw); // 질문 검색 메서드 이용해서 가져오기
		int size = 10; // 1페이지당 10페이지
		int startRow = page * size;  // 1페이지가 0으로 들어오게 해야 nativeQuery에서 제대로 쓸 수 있음 (startRow 초과로 되어 있어서)
		int endRow = startRow + size;
		
		// 검색어 없이 리스트 조회
		List<Question> pageQuestionList = questionRepository.findAllWithPaging(startRow, endRow);
		long totalQuestion = questionRepository.count(); // 모든 글 개수 가져오기
		// 검색 결과 리스트 조회
		List<Question> searchQuestionList = questionRepository.searchAllWithPaging(kw, startRow, endRow);
		int totalSearchQuestion = questionRepository.countSearchResult(kw); // 검색 결과 글 개수 가져오기

		
		// 제공해주는 Page 이용해서 타입 변경, 구현체 이용
//		Page<Question> pagingList = new PageImpl<>(pageQuestionList, PageRequest.of(page, size), totalQuestion);
		Page<Question> pagingList = new PageImpl<>(searchQuestionList, PageRequest.of(page, size), totalSearchQuestion);
		return pagingList;
	}
	
	// 기본키를 통해서 질문 1개 가져오기
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
	public void create(String subject, String content, SiteUser user) {
		Question question = new Question();
		question.setSubject(subject);
		question.setContent(content);
		question.setCreatedate(LocalDateTime.now());
		question.setAuthor(user);
		questionRepository.save(question);
	}
	
	
	
	// 질문 수정하기 (변경된 제목과 내용, 그리고 엔티티 Question을 매개변수로 받아야 함)
	public void modify(Question question, String subject, String content) {
		question.setSubject(subject);
		question.setContent(content);
		question.setModifydate(LocalDateTime.now());
		questionRepository.save(question);
	}
	
	// 질문 삭제하기
	public void delete(Question question) {
		questionRepository.delete(question);
	}
	
	
	// 질문 추천 (update문)
	public void vote(Question question, SiteUser siteUser) {
		question.getVoter().add(siteUser); // 질문글의 voter를 가져와서 siteUser 엔티티에 값을 추가
		questionRepository.save(question); // 변경된 question 다시 저장
	}
	
	// 질문 비추
	public void dislike(Question question, SiteUser siteUser) {
		question.getDislikeusers().add(siteUser);
		questionRepository.save(question);
	}

	
	// 질문 조회수 증가
	public void hit(Question question) {
		question.setHit(question.getHit() + 1);
		questionRepository.save(question);
	}
	
	
	/*
	 select
	    distinct q.id,
	    q.author_id,
	    q.content,
	    q.create_date,
	    q.modify_date,
	    q.subject 
	from question q 
	left outer join site_user u1 on q.author_id=u1.id 
	left outer join answer a on q.id=a.question_id 
	left outer join site_user u2 on a.author_id=u2.id 
	where
	    q.subject like '%스프링%' 
	    or q.content like '%스프링%' 
	    or u1.username like '%스프링%' 
	    or a.content like '%스프링%' 
	    or u2.username like '%스프링%' 
	 */
	// 질문 검색 (Specification 인터페이스 이용)
	private Specification<Question> search(String kw) {
		return new Specification<Question>() {
			private static final long SerialVersionUID = 1L; // 위조 방지..?
			@Override
			public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 쿼리가 들어 갈 자리
				query.distinct(true); // 쿼리 중복 제거
				Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT); // Question + SiteUser LEFT 조인
				Join<Question, Answer> a = q.join("answerLis", JoinType.LEFT); // Question + Answer LEFT 조인
				Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT); // Answer + SiteUser LEFT 조인
				// 검색결과 (or로 묶기, like 이용 ..)
				return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
						cb.like(q.get("content"), "%" + kw + "%"), // 내용
						cb.like(u1.get("username"), "%" + kw + "%"), // 질문 작성자
						cb.like(a.get("content"), "%" + kw + "%"), // 답변 내용
						cb.like(u2.get("username"), "%" + kw + "%") // 답변 작성자
						);
			}
		};
	}
	
}
 