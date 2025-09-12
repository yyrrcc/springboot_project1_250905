package com.mycompany.p1.question;

import java.util.List;

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
	
	// 검색 전용 메서드 (Specification과 Pageable 객체를 사용하여 DB에서 Question 엔티티를 조회한 결과를 페이징하여 반환)
	//Page<Question> findAll(Specification<Question> spec, Pageable pageable);
	
	
	// native Query 사용해서 검색 결과를 가져와서 페이징 하는 메서드
	@Query(value = 
		       "SELECT * FROM ( " +
		       "   SELECT q.*, ROWNUM rnum FROM ( " +
		       "       SELECT DISTINCT q.* " +
		       "       FROM question q " +
		       "       LEFT OUTER JOIN siteuser u1 ON q.author_id = u1.id " +
		       "       LEFT OUTER JOIN answer a ON a.question_id = q.id " +
		       "       LEFT OUTER JOIN siteuser u2 ON a.author_id = u2.id " +
		       "       WHERE q.subject LIKE '%' || :kw || '%' " +
		       "          OR q.content LIKE '%' || :kw || '%' " +
		       "          OR u1.username LIKE '%' || :kw || '%' " +
		       "          OR a.content LIKE '%' || :kw || '%' " +
		       "          OR u2.username LIKE '%' || :kw || '%' " +
		       "       ORDER BY q.createdate DESC " +
		       "   ) q WHERE ROWNUM <= :endRow " +
		       ") WHERE rnum > :startRow", 
		       nativeQuery = true)
	public List<Question> searchAllWithPaging(@Param("kw") String kw, @Param("startRow") int startRow, @Param("endRow") int endRow);
	
	
	
	
	
	// native Query 사용해서 검색 결과의 총 개수 구하는 메서드
		@Query(value = 
			       "SELECT COUNT(DISTINCT q.id) " +
			       "       FROM question q " +
			       "       LEFT OUTER JOIN siteuser u1 ON q.author_id = u1.id " +
			       "       LEFT OUTER JOIN answer a ON a.question_id = q.id " +
			       "       LEFT OUTER JOIN siteuser u2 ON a.author_id = u2.id " +
			       "       WHERE q.subject LIKE '%' || :kw || '%' " +
			       "          OR q.content LIKE '%' || :kw || '%' " +
			       "          OR u1.username LIKE '%' || :kw || '%' " +
			       "          OR a.content LIKE '%' || :kw || '%' " +
			       "          OR u2.username LIKE '%' || :kw || '%' " +
			       "       ORDER BY q.createdate DESC ", 
			       nativeQuery = true)
		public int countSearchResult(@Param("kw") String kw);
		
	
}
