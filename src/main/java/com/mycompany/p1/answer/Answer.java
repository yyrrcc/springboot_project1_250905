package com.mycompany.p1.answer;

import java.time.LocalDateTime;
import java.util.Set;

import com.mycompany.p1.question.Question;
import com.mycompany.p1.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "answer")
@SequenceGenerator(
		name = "ANSWER_SEQ_GENERATOR",
		sequenceName = "ANSWER_SEQ",
		initialValue = 1,
		allocationSize = 1
		)
public class Answer {
	
    @Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ANSWER_SEQ_GENERATOR") 
    private Integer id; 

    @Column(length = 500) 
    private String content; 

    private LocalDateTime createdate; 

	
	// 답변:질문(N:1) 관계
    @ManyToOne
    private Question question;
    
    // 답변:작성자(N:1) 관계
    @ManyToOne
    private SiteUser author;
    
    // 수정날짜
    private LocalDateTime modifydate;
    
    // 답변:추천자(N:N) 관계, 중복 안 됨(Set)은 결국 유저의 수 = 추천 수
    @ManyToMany
    private Set<SiteUser> voter;
    
    // 질문:비추천자(N:N) 관계
    @ManyToMany
    private Set<SiteUser> dislikeusers;
}
