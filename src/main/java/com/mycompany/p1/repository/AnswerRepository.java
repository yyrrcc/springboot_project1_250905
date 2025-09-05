package com.mycompany.p1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.p1.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

}
