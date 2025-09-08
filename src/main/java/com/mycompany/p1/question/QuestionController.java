package com.mycompany.p1.question;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/question")
public class QuestionController {

//	@Autowired
//    private QuestionRepository questionRepository;
	@Autowired
	private QuestionService questionService;
	
	@GetMapping (value = "/")
	public String root() {
		return "redirect:/question/list";
	}

	@GetMapping (value = "/list")
	public String list(Model model) {
		//List<Question> questions = questionRepository.findAll();
		List<Question> questions = questionService.getList();
		model.addAttribute("questions", questions);
		return "question_list";
	}
	
	@GetMapping (value = "/detail/{id}") // 파라미터 이름 없이 값만 받아온다
	public String detail(Model model, @PathVariable("id") Integer id) {
		Question question = questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";
	}
}
