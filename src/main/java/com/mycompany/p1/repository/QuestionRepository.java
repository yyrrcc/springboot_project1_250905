package com.mycompany.p1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.p1.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
	
	// 글 제목으로 찾기
	public Question findBySubject(String subject);

}
