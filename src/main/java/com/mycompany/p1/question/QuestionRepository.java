package com.mycompany.p1.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
	
	// 글 제목으로 찾기
	public Question findBySubject(String subject);
	
	// 글과 내용으로 찾기 (리스트 반환인데 간단 ver.)
	public Question findBySubjectAndContent(String subject, String content);
	
	// 특정 문자열을 포함한 데이터 찾기
	public List<Question> findBySubjectLike(String subject);
	
	// 페이징 관련 메서드 (jpa 관련 라이브러리에 이미 페이징 관련된 것들이 포함 되어 있음)
	//public Page<Question> findAll(Pageable pageable);

	// 페이징 관련 메서드 (nativeQuery 사용)
	@Query(
			value = "SELECT * FROM ( " +
					" SELECT q.*, ROWNUM rnum FROM ( " +
					"   SELECT * FROM question ORDER BY createdate DESC " +
					" ) q WHERE ROWNUM <= :endRow " +
					") WHERE rnum > :startRow",
					nativeQuery = true)
	public List<Question> findAllWithPaging(@Param("startRow") int startRow, @Param("endRow") int endRow);
	
}
