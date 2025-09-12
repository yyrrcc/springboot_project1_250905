package com.mycompany.p1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	
	@GetMapping(value = "/") // 오라클 클라우드 root 요청 처리
//	@GetMapping(value = "/") // 로컬용 root 요청 처리
	public String index() {
		return "redirect:/question/list";
	}
}
