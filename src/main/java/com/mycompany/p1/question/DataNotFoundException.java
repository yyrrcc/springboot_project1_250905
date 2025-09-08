package com.mycompany.p1.question;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 예외가 발생하게 되면 에러 응답 코드를 내보내주는 ..
@ResponseStatus (value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotFoundException extends RuntimeException {
	private static final long SERIALVERSIONUID = 1L;
	
	public DataNotFoundException(String message) {
		super(message);
	}

}
