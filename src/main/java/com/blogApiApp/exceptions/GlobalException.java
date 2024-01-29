package com.blogApiApp.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.function.EntityResponse;

import com.blogApiApp.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex)
	{
		String message=ex.getMessage();
		
		return new ResponseEntity<ApiResponse> (new ApiResponse(message, false), HttpStatus.NOT_FOUND);
		
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex)
	{
		Map<String, String> response= new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName=((FieldError)error).getField();
			String message=error.getDefaultMessage();
			response.put(fieldName, message);
		});
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
}
