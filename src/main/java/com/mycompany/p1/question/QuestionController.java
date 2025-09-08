package com.mycompany.p1.question;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuestionController {

	@Autowired
    private QuestionRepository questionRepository;
	
	@GetMapping (value = "/")
	public String root() {
		return "redirect:/question/list";
	}

	@GetMapping (value = "/question/list")
	public String list(Model model) {
		List<Question> questions = questionRepository.findAll();
		model.addAttribute("questions", questions);
		return "question_list";
	}

}
