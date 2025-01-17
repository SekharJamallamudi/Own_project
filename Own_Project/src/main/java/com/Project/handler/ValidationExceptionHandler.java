package com.Project.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.Project.exception.EmailAlreadyExistsException;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
@RestControllerAdvice
public class ValidationExceptionHandler { 
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex){
		Map<String, String> errorMap = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errorMap.put(error.getField(), error.getDefaultMessage());
		});
		return errorMap;
	}
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public Map<String,String>usernotFoundById(EmailAlreadyExistsException ex)
	{
		Map<String,String>errorMap = new HashMap<>();
		errorMap.put("errorMessage",ex.getMessage());
		return errorMap;
	}
}
