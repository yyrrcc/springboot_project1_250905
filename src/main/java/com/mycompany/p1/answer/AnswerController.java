package com.mycompany.p1.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.p1.question.Question;
import com.mycompany.p1.question.QuestionService;

@Controller
@RequestMapping ("/answer")
public class AnswerController {
	
	@Autowired
	private QuestionService questionService;
	@Autowired
	private AnswerService answerService;

	@PostMapping (value = "/create/{id}") // 답변 form action으로 온 값
	// 넘겨주기 위해 Model, 넘어온 id값과 content값
	public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam("content") String content) {
		Question question = questionService.getQuestion(id); // 질문글 가져오기
		answerService.create(question, content); // 답변 등록
		
		return String.format("redirect:/question/detail/%s", id);
		
	}
}
