package com.mycompany.p1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.p1.entity.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

}
