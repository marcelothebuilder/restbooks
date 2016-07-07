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

import io.github.marcelothebuilder.restbooks.domain.Comentario;
import io.github.marcelothebuilder.restbooks.domain.Livro;
import io.github.marcelothebuilder.restbooks.service.LivrosService;
import io.github.marcelothebuilder.restbooks.service.exceptions.LivroInexistenteException;

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
	private LivrosService livrosService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Livro>> listar() {
		List<Livro> todosLivros = livrosService.listar();
		return ResponseEntity.status(HttpStatus.OK).body(todosLivros);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> salvar(@RequestBody Livro livro) {
		Livro livroCriado = livrosService.salvar(livro);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(livroCriado.getCodigo())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{codigo}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@RequestBody Livro livro, @PathVariable("codigo") Long codigo)
			throws LivroInexistenteException {
		livro.setCodigo(codigo);
		livrosService.atualizar(livro);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ResponseEntity<Livro> buscar(@PathVariable("codigo") Long codigo) throws LivroInexistenteException {
		Livro livro = livrosService.buscar(codigo);
		return ResponseEntity.status(HttpStatus.OK).body(livro);
	}

	@RequestMapping(value = "/{codigo}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@PathVariable("codigo") Long codigo) throws LivroInexistenteException {
		livrosService.deletar(codigo);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@RequestMapping(value = "/{id}/comentarios", method = RequestMethod.POST)
	public ResponseEntity<Void> adicionarComentario(@PathVariable("id") Long codigoLivro,
			@RequestBody Comentario comentario) throws LivroInexistenteException {

		livrosService.salvarComentario(codigoLivro, comentario);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

		return ResponseEntity.created(uri).build();
	}

}
