package com.mycompany.p1.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	// 회원가입 폼
	@GetMapping(value = "/signup")
	public String signup(UserCreateForm userCreateForm) {
		return "signup_form";
	}
	// 회원가입
	@PostMapping(value = "/signup")
	// 유효성 검증을 위한 UserCreateForm 가져오기
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult result) {
		if (result.hasErrors()) {
			return "signup_form";
		}
		if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			result.rejectValue("password2", "passwordIncorrect", "비밀번호 확인이 일치하지 않습니다.");
			return "signup_form";
		}
		try {
			userService.create(userCreateForm.getUsername(), userCreateForm.getPassword1(), userCreateForm.getEmail());
		} catch (DataIntegrityViolationException e) { // 아이디 또는 이메일 중복 에러
			e.printStackTrace();
			result.reject("signupFailed", "이미 사용중인 아이디나 이메일입니다.");
			return "signup_form";
		} catch (Exception e) { // 그 밖에 다른 예외
			e.printStackTrace();
			result.reject("signupFailed", "회원가입 실패입니다.");
		}
		return "redirect:/";
	}
	
	
}
