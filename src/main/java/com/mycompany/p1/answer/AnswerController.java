package com.mycompany.p1.answer;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.mycompany.p1.SecurityConfig;
import com.mycompany.p1.question.Question;
import com.mycompany.p1.question.QuestionService;
import com.mycompany.p1.user.SiteUser;
import com.mycompany.p1.user.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping ("/answer")
public class AnswerController {

    private final SecurityConfig securityConfig;
	
	@Autowired
	private QuestionService questionService;
	@Autowired
	private AnswerService answerService;
	@Autowired
	private UserService userService;

    AnswerController(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    @PreAuthorize("isAuthenticated()") // 권한 있는 경우에만 할 수 있게
	@PostMapping (value = "/create/{id}") // 답변 form action으로 온 값
	// 넘겨주기 위해 Model, 넘어온 id값과 content값, Principal 객체 안에 로그인한 사용자의 정보가 들어 있음
	public String createAnswer(Model model, @PathVariable("id") Integer id, 
			@Valid AnswerForm answerForm, BindingResult result, Principal principal) {
		Question question = questionService.getQuestion(id); // 질문글 가져오기
		//principal.getName(); // 로그인한 유저의 아이디 얻기
		SiteUser siteUser = userService.getUser(principal.getName()); // 아이디 값으로 유저의 정보 가져오기
		if (result.hasErrors()) {
			model.addAttribute("question", question); // question을 모델에 넣어 보내줘야 함
			return "question_detail";
		}
		answerService.create(question, answerForm.getContent(), siteUser); // 답변 등록
		
		return String.format("redirect:/question/detail/%s", id);
	}
    
    
    
    
    // 답변 수정 폼
    @GetMapping (value = "/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {
    	Answer answer = answerService.getAnswer(id); // 기본키로 Answer 엔티티 가져오기
    	if (!answer.getAuthor().getUsername().equals(principal.getName())) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
    	}
    	answerForm.setContent(answer.getContent()); // 기본키로 가져온 answer의 content 가져와서 answerForm set으로 보내기
    	return "answer_form"; // 댓글 수정하는 폼으로 이동
    }
    
    // 답변 수정 하고난 후
    @PostMapping (value = "/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String answerModify(@Valid AnswerForm answerForm, @PathVariable("id") Integer id, BindingResult result, Principal principal) {
    	// 에러의 유무 먼저 확인
    	if (result.hasErrors()) {
    		return "answer_form";
    	}
    	 // 수정 하기 전 원본
    	Answer answer = answerService.getAnswer(id);
    	// 수정 권한 확인
    	if(!answer.getAuthor().getUsername().equals(principal.getName())) {
    		 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
    	}
    	// 수정 완료
    	answerService.modify(answer, answerForm.getContent());
    	// 답변의 질문 id 값으로 이동해야함!
    	return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }
    
    
    
    
    // 답변 삭제하기
    @GetMapping (value = "/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String answerDelete(@PathVariable("id") Integer id, Principal principal) {
    	Answer answer = answerService.getAnswer(id);
    	if (!answer.getAuthor().getUsername().equals(principal.getName())) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
    	}
    	answerService.delete(answer);
    	return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }

    
	
}
