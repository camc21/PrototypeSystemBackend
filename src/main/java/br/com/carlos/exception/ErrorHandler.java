package br.com.carlos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.Value;

@RestControllerAdvice
public class ErrorHandler {
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ExceptionRestResponse handleCustomException(Exception exception) {
		return new ExceptionRestResponse(500, exception.getMessage());
	}
	
	@Value
	public static class ExceptionRestResponse {
		int code;
		String message;
	}
}
