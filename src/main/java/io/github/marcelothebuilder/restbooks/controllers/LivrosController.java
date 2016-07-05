package io.github.marcelothebuilder.restbooks.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.github.marcelothebuilder.restbooks.domain.Livro;
import io.github.marcelothebuilder.restbooks.repository.Livros;

/**
 * Controller que fornece o resource de Livros.
 * 
 * @author Marcelo Paixao Resende
 *
 */
@RestController
public class LivrosController {
	
	@Autowired
	private Livros livros;
	

	@RequestMapping(value = "/livros", method = RequestMethod.GET)
	@ResponseBody
	public List<Livro> listar() {
		return livros.todos();
	}

}
