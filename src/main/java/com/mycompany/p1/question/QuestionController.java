package com.mycompany.p1.question;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.p1.answer.AnswerForm;

import jakarta.validation.Valid;

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
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		Question question = questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";
	}
	
    @GetMapping("/create")
    // 유효성 검증 추가로 인해 매개변수 questionForm를 받았다
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    // Validation 하기 전 메서드
//	@PostMapping (value = "/create")
//	// 메서드 오버로딩, request.getParameter 대신 @RequestParam 사용
//	public String questionCreate(@RequestParam("subject") String subject, @RequestParam("content") String content) {
//		questionService.create(subject, content);
//		return "redirect:/question/list";
//	}
	
	@PostMapping (value = "/create")
	// @Valid 유효성 체크를 하기 위해 적어주기. 에러가 존재한다면 폼으로 돌아가게 처리. 매개변수를 클래스로 받았음.
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult result) {
		
		// bind : 묶는다는 뜻. 에러가 있으면 모델로 굳이 안 보내줘도 매개변수와 에러 모두 포함해서 넘겨줌
		if (result.hasErrors()) { 
			return "question_form";
		}
		questionService.create(questionForm.getSubject(), questionForm.getContent());
		return "redirect:/question/list";
	}
	
	
}