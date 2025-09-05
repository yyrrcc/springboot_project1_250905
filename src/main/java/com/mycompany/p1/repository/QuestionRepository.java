package com.mycompany.p1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.p1.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

}
