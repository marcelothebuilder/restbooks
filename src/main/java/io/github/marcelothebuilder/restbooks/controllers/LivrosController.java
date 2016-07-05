package io.github.marcelothebuilder.restbooks.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.marcelothebuilder.restbooks.domain.Livro;
import io.github.marcelothebuilder.restbooks.repository.LivroInexistenteException;
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
	public ResponseEntity<List<Livro>> listar() {
		List<Livro> todosLivros = livros.todos();
		return ResponseEntity.status(HttpStatus.OK).body(todosLivros);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> salvar(@RequestBody Livro livro) {
	Livro livroCriado = livros.salvar(livro);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(livroCriado.getCodigo())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{codigo}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@RequestBody Livro livro, @PathVariable("codigo") Long codigo) {
		livro.setCodigo(codigo);
		livros.salvar(livro);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{codigo}", method = RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable("codigo") Long codigo) {
		Livro livro = livros.buscar(codigo);
		
		if (livro == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(livro);
	}
	
	@RequestMapping(value="/{codigo}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@PathVariable("codigo") Long codigo){
		try {
			livros.deletar(codigo);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (LivroInexistenteException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

}
