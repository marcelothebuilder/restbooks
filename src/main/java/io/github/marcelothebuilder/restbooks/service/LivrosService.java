package io.github.marcelothebuilder.restbooks.service;

import java.util.List;

import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.marcelothebuilder.restbooks.domain.Comentario;
import io.github.marcelothebuilder.restbooks.domain.Livro;
import io.github.marcelothebuilder.restbooks.repository.Comentarios;
import io.github.marcelothebuilder.restbooks.repository.Livros;
import io.github.marcelothebuilder.restbooks.service.exceptions.LivroInexistenteException;

@Service
public class LivrosService {

	@Autowired
	private Livros livros;
	
	@Autowired
	private Comentarios comentarios;
	
	public List<Livro> listar() {
		return livros.todos();
	}
	
	public Livro buscar(Long codigo) throws LivroInexistenteException {
		Livro livro = livros.buscar(codigo);
		
		if (livro == null) {
			throw new LivroInexistenteException(String.format("Livro de código %d não existe.", codigo));
		}
		
		return livro;
	}
	
	public Livro salvar(Livro livro) {
		livro.setCodigo(null);
		return livro = livros.salvar(livro);
	}
	
	public void deletar(Long codigo) throws LivroInexistenteException {
		try {
			livros.deletar(codigo);
		} catch (DataAccessException e) {
			throw new LivroInexistenteException(e.getMessage(), e);
		}
	}
	
	public void atualizar(Livro livro) throws LivroInexistenteException {
		boolean livroNaoExiste = !this.verificarExistencia(livro);
		
		if (livroNaoExiste) {
			throw new LivroInexistenteException(String.format("Livro de código %d não existe.", livro.getCodigo()));
		}
	}
	
	public Comentario salvarComentario(Long codigoLivro, Comentario comentario) throws LivroInexistenteException {
		if (!this.verificarExistencia(codigoLivro)) {
			throw new LivroInexistenteException(String.format("Livro de código %d não existe", codigoLivro));
		}
		
		return comentarios.salvar(comentario);
	}
	
	private boolean verificarExistencia(Livro livro) {
		return this.verificarExistencia(livro.getCodigo());
	}
	
	private boolean verificarExistencia(Long codigoLivro) {
		try {
			this.buscar(codigoLivro);
			return true;
		} catch (LivroInexistenteException e) {
			return false;
		}
	}
	
}
