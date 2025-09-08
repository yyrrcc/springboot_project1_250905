package com.mycompany.p1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.p1.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
	
	// 글 제목으로 찾기
	public Question findBySubject(String subject);
	
	// 글과 내용으로 찾기 (리스트 반환인데 간단 ver.)
	public Question findBySubjectAndContent(String subject, String content);
	
	// 특정 문자열을 포함한 데이터 찾기
	public List<Question> findBySubjectLike(String subject);

}
