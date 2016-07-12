package io.github.marcelothebuilder.restbooks.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

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

import io.github.marcelothebuilder.restbooks.dto.ComentarioDTO;
import io.github.marcelothebuilder.restbooks.dto.LivroDTO;
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
	public ResponseEntity<List<LivroDTO>> listar() {
		List<LivroDTO> todosLivros = livrosService.listar();
		return ResponseEntity.status(HttpStatus.OK).body(todosLivros);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> salvar(@Valid @RequestBody LivroDTO livro) {
		LivroDTO livroCriado = livrosService.salvar(livro);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(livroCriado.getCodigo())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{codigo}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@Valid @RequestBody LivroDTO livro, @PathVariable("codigo") Long codigo)
			throws LivroInexistenteException {
		livro.setCodigo(codigo);
		livrosService.atualizar(livro);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ResponseEntity<LivroDTO> buscar(@PathVariable("codigo") Long codigo) throws LivroInexistenteException {
		LivroDTO livro = livrosService.buscar(codigo);
		return ResponseEntity.status(HttpStatus.OK).body(livro);
	}

	@RequestMapping(value = "/{codigo}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@PathVariable("codigo") Long codigo) throws LivroInexistenteException {
		livrosService.deletar(codigo);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@RequestMapping(value = "/{id}/comentarios", method = RequestMethod.POST)
	public ResponseEntity<Void> adicionarComentario(@PathVariable("id") Long codigoLivro,
			@RequestBody ComentarioDTO comentario) throws LivroInexistenteException {

		livrosService.salvarComentario(codigoLivro, comentario);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}/comentarios", method = RequestMethod.GET)
	public ResponseEntity<List<ComentarioDTO>> listarComentarios(@PathVariable("id") Long codigoLivro)
			throws LivroInexistenteException {
		List<ComentarioDTO> comentarios = livrosService.listarComentarios(codigoLivro);
		return ResponseEntity.ok(comentarios);
	}

}
