package io.github.marcelothebuilder.restbooks.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/livros")
public class LivrosController {
	
	@Autowired
	private Livros livros;
	

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Livro> listar() {
		return livros.todos();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public void salvar(@RequestBody Livro livro) {
		
		System.out.println(livro);
		
		livros.salvar(livro);
	}
	
	@RequestMapping(value="/{codigo}", method = RequestMethod.GET)
	public Livro buscar(@PathVariable("codigo") Long codigo) {
		return livros.buscar(codigo);
	}
	
	@RequestMapping(value="/{codigo}", method = RequestMethod.DELETE)
	public void deletar(@PathVariable("codigo") Long codigo){
		System.out.println("Deletando livro");
		livros.deletar(codigo);
		System.out.println("Deletado");
	}

}
