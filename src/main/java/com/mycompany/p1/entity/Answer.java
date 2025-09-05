package com.mycompany.p1.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	
	// N:1(답변:질문) 관계
    @ManyToOne
    private Question question;
    

}
