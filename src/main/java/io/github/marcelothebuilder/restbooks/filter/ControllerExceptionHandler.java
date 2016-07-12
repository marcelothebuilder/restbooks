package io.github.marcelothebuilder.restbooks.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.github.marcelothebuilder.restbooks.service.exceptions.AutorInexistenteException;
import io.github.marcelothebuilder.restbooks.service.exceptions.LivroInexistenteException;

@ControllerAdvice
public class ControllerExceptionHandler {
	@ExceptionHandler(LivroInexistenteException.class)
	public ResponseEntity<Void> handleLivroInexistenteException(LivroInexistenteException e, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@ExceptionHandler(AutorInexistenteException.class)
	public ResponseEntity<Void> handleAutorInexistenteException(AutorInexistenteException e, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}
