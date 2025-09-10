package com.mycompany.p1.question;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.mycompany.p1.answer.AnswerForm;
import com.mycompany.p1.user.SiteUser;
import com.mycompany.p1.user.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/question")
public class QuestionController {

//	@Autowired
//    private QuestionRepository questionRepository;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private UserService userService;
	
	@GetMapping (value = "/")
	public String root() {
		return "redirect:/question/list";
	}

	
	@GetMapping (value = "/list")
	// 파라미터 값으로 page 받고, 기본값(defaultValue)은 0으로 초기화
	public String list(Model model) {
		//List<Question> questions = questionRepository.findAll();
		List<Question> questions = questionService.getList();
		model.addAttribute("questions", questions);
		return "question_list";
	}
	
	
	// 페이징 
//	@GetMapping (value = "/list")
//	// 파라미터 값으로 page 받고, 기본값(defaultValue)은 0으로 초기화
//	public String list(Model model, @RequestParam (value = "page", defaultValue = "0") int page) {
//		Page<Question> paging = questionService.getList(page); // n페이지당 10개의 글 리스트
//		model.addAttribute("paging", paging);
//		return "question_list";
//	}
	
	@GetMapping (value = "/detail/{id}") // 파라미터 이름 없이 값만 받아온다
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		Question question = questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";
	}
	
	@PreAuthorize("isAuthenticated()") // principal null값 오류를 잡아주기 위해. 로그인한 경우에만 실행할 수 있게.
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
	
    @PreAuthorize("isAuthenticated()") // principal null값 오류를 잡아주기 위해. 로그인한 경우에만 실행할 수 있게.
	@PostMapping (value = "/create")
	// @Valid 유효성 체크를 하기 위해 적어주기. 에러가 존재한다면 폼으로 돌아가게 처리. 매개변수를 클래스로 받았음.
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult result, Principal principal) {
		
		// bind : 묶는다는 뜻. 에러가 있으면 모델로 굳이 안 보내줘도 매개변수와 에러 모두 포함해서 넘겨줌
		if (result.hasErrors()) { 
			return "question_form";
		}
		// pricipal에 저장된 로그인한 아이디를 가져와서 유저 정보 저장
		SiteUser siteUser = userService.getUser(principal.getName());
		questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
		return "redirect:/question/list";
	}
    
    
    // 질문 수정 버튼 눌렀을 때 폼 띄우기
    @GetMapping (value = "/modify/{id}")
    @PreAuthorize("isAuthenticated()") // 로그인 안하면 수정 못하게 또 막기
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
    	Question question = questionService.getQuestion(id); // 수정하려는 글 내용 가져오기
    	if(!question.getAuthor().getUsername().equals(principal.getName())) { // 수정권한 확인하기
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
    	}
    	questionForm.setSubject(question.getSubject()); // 수정버튼 눌렀을 때 글 내용 보이게 question.get
    	questionForm.setContent(question.getContent()); // 그 다음 수정한 글들은 questionForm.set으로 저장
    	return "question_form";
    }
    
    
    
    // 글 수정
    @PostMapping (value = "/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String questionModify(@PathVariable("id") Integer id, @Valid QuestionForm questionForm,
    		BindingResult result, Principal principal) {
    	if (result.hasErrors()) {
    		return "question_form";
    	}
    	Question question = questionService.getQuestion(id);
    	if (!question.getAuthor().getUsername().equals(principal.getName())) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
    	}
    	questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
    	return String.format("redirect:/question/detail/%s", id);
    }
    
    
    // 글 삭제
    @GetMapping (value = "/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String questionDelete(@PathVariable("id") Integer id, Principal principal) {
    	Question question = questionService.getQuestion(id);
    	if (!question.getAuthor().getUsername().equals(principal.getName())) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
    	}
    	questionService.delete(question);
    	return "redirect:/question/list";
    }
    
    
    // 글 추천
    @PreAuthorize("isAuthenticated()")
    @GetMapping (value = "/vote/{id}")
    public String recommend(@PathVariable("id") Integer id, Principal principal) {
    	Question question = questionService.getQuestion(id);
    	SiteUser siteUser = userService.getUser(principal.getName());
    	questionService.vote(question, siteUser);
    	return String.format("redirect:/question/detail/%s", id);
    }
	
	
}