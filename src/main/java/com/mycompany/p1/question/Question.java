package com.mycompany.p1.question;

import java.time.LocalDateTime;
import java.util.List;

import com.mycompany.p1.answer.Answer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "question")
@SequenceGenerator(
		name = "QUESTION_SEQ_GENERATOR", // JPA 내부 시퀀스 이름 (기존 시퀀스 이름 + generator 붙이는 게 관례)
		sequenceName = "QUESTION_SEQ", // DB의 실제 시퀀스 이름
		initialValue = 1, // 시퀀스 시작값
		allocationSize = 1 // 시퀀스 증가치
		)
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QUESTION_SEQ_GENERATOR") // 자동증가옵션
	private Integer id; // 기본키, 자동증가

	@Column(length = 200)
	private String subject;
	
	@Column(length = 500)
	private String content;
	
	private LocalDateTime createdate;
	
	// 1:N(질문:답변) 관계, mappedBy : 참조 엔티티의 속성명을 정의, 질문글이 삭제되면 답변글도 삭제되게 해주는 옵션
    @OneToMany (mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

}
