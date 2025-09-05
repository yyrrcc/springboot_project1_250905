package com.mycompany.p1.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 자동증가옵션 (시퀀스가 따로 없음)
	private Integer id; // 기본키, 자동증가

	@Column(length = 200)
	private String subject;
	
	@Column(columnDefinition = "TEXT") // 글자 제한이 없음
	private String content;
	
	@CreatedDate
	private LocalDateTime createDate;
	
	// 1:N(질문:답변) 관계, mappedBy : 참조 엔티티의 속성명을 정의, 질문글이 삭제되면 답변글도 삭제되게 해주는 옵션
    @OneToMany (mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

}
