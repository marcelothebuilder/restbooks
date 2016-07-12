package io.github.marcelothebuilder.restbooks.controllers;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.marcelothebuilder.restbooks.dto.AutorDTO;
import io.github.marcelothebuilder.restbooks.service.AutoresService;

@RestController
@RequestMapping("/autores")
public class AutoresController {

	@Autowired
	private AutoresService service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<AutorDTO>> listar() {
		return ResponseEntity.ok(service.listar());
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> salvar(@RequestBody AutorDTO autor) {

		AutorDTO autorSalvo = service.salvar(autor);

		Map<String, Object> uriVars = new LinkedHashMap<>(1);

		uriVars.put("codigo", autorSalvo.getCodigo());

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}").build().expand(uriVars).toUri();

		return ResponseEntity.created(uri).build();

	}
}
