package io.github.marcelothebuilder.restbooks.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.github.marcelothebuilder.restbooks.dto.AutorDTO;
import io.github.marcelothebuilder.restbooks.service.AutoresService;

@RestController
@RequestMapping("/autores")
public class AutoresController {
	
	@Autowired
	private AutoresService service;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<AutorDTO>> listar() {
		return ResponseEntity.ok(service.listar());
	}
}
