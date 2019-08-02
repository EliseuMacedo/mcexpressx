package com.mcexpress.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mcexpress.services.exceptions.ObjectNotFountException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	//esses objetos e métodos são padrões do controler advice
	
	//essa anotação indica que é um tratador de exceções desse tipo de exceção
	@ExceptionHandler(ObjectNotFountException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFountException e, HttpServletRequest request){
		
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		
		return ResponseEntity.status( HttpStatus.NOT_FOUND).body(err);
		
	}
	

}
