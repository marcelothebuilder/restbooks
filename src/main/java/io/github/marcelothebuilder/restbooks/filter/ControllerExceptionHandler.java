package io.github.marcelothebuilder.restbooks.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.github.marcelothebuilder.restbooks.dto.InexistenteErrorDTO;
import io.github.marcelothebuilder.restbooks.service.exceptions.InexistenteException;

@ControllerAdvice
public class ControllerExceptionHandler {
	@ExceptionHandler(InexistenteException.class)
	public ResponseEntity<InexistenteErrorDTO> handleRegistroInexistenteException(InexistenteException e, HttpServletRequest request) {
		InexistenteErrorDTO inexistenteErrorDTO = new InexistenteErrorDTO();
		inexistenteErrorDTO.setMensagem(e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(inexistenteErrorDTO);
	}
}
