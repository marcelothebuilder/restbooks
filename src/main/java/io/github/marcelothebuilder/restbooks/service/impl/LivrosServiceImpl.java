package io.github.marcelothebuilder.restbooks.service.impl;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.marcelothebuilder.restbooks.dto.AutorDTO;
import io.github.marcelothebuilder.restbooks.dto.ComentarioDTO;
import io.github.marcelothebuilder.restbooks.dto.LivroDTO;
import io.github.marcelothebuilder.restbooks.model.Comentario;
import io.github.marcelothebuilder.restbooks.model.Livro;
import io.github.marcelothebuilder.restbooks.repository.Comentarios;
import io.github.marcelothebuilder.restbooks.repository.Livros;
import io.github.marcelothebuilder.restbooks.service.AbstractLivrosService;
import io.github.marcelothebuilder.restbooks.service.exceptions.LivroInexistenteException;
import io.github.marcelothebuilder.restbooks.util.PojoUtils;

@Service
public class LivrosServiceImpl extends AbstractLivrosService {

	@Autowired
	private Livros livros;
	
	@Autowired
	private Comentarios comentarios;
	
	@Override
	public List<LivroDTO> listar() {
		List<LivroDTO> list = livros.todos()
			.stream()
			.map(LivrosServiceImpl.getModelMapper())
			.collect(Collectors.toList());
		
		return list;
	}
	
	@Override
	public LivroDTO buscar(Long codigo) throws LivroInexistenteException {
		Livro livro = livros.buscar(codigo);
		
		System.out.println(livro);
		
		if (livro == null) {
			throw new LivroInexistenteException(String.format("Livro de código %d não existe.", codigo));
		}
		
		LivroDTO dto = LivrosServiceImpl.toDto(livro);
		
		return dto;
	}

	@Override
	public LivroDTO salvar(LivroDTO livroDto) {
		Livro livro = fromDto(livroDto);
		livro.setCodigo(null);
		livro = livros.salvar(livro);
		return toDto(livro);
	}
	
	

	@Override
	public void deletar(Long codigo) throws LivroInexistenteException {
		try {
			livros.deletar(codigo);
		} catch (DataAccessException e) {
			throw new LivroInexistenteException(e.getMessage(), e);
		}
	}
	
	@Override
	public LivroDTO atualizar(LivroDTO livro) throws LivroInexistenteException {
		boolean livroNaoExiste = !this.verificarExistencia(livro.getCodigo());
		
		if (livroNaoExiste) {
			throw new LivroInexistenteException(String.format("Livro de código %d não existe.", livro.getCodigo()));
		}
		
		return toDto(livros.salvar(fromDto(livro)));
	}
	
	@Override
	public ComentarioDTO salvarComentario(Long codigoLivro, ComentarioDTO comentario) throws LivroInexistenteException {
		if (!this.verificarExistencia(codigoLivro)) {
			throw new LivroInexistenteException(String.format("Livro de código %d não existe", codigoLivro));
		}
		
		return comentarioToDto(comentarios.salvar(comentarioFromDto(comentario)));
	}

	@Override
	public List<ComentarioDTO> listarComentarios(Long codigoLivro) throws LivroInexistenteException {
		if (!this.verificarExistencia(codigoLivro)) {
			throw new LivroInexistenteException(String.format("Livro de código %d não existe", codigoLivro));
		}
		
		return comentarios.buscarPorCodigoLivro(codigoLivro).stream()
				.map(LivrosServiceImpl::comentarioToDto)
				.collect(Collectors.toList());
	}
	
	@SuppressWarnings("unused")
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
	
	private static Function<Livro, LivroDTO> getModelMapper() {
		Function<Livro, LivroDTO> mapping = model -> {
			LivroDTO dto = PojoUtils.copyProperties(model, LivroDTO.class);
			AutorDTO autorDto = PojoUtils.copyProperties(model.getAutor(), AutorDTO.class);
			Set<ComentarioDTO> comentariosDto = model.getComentarios()
					.stream()
					.map(LivrosServiceImpl.getComentarioModelMapper())
					.collect(Collectors.toSet());
			
			dto.setAutor(autorDto);
			dto.setComentarios(comentariosDto);
			
			return dto;
		};
		return mapping;
	}
	
	private static Function<Comentario, ComentarioDTO> getComentarioModelMapper() {
		 return comentario -> {
			ComentarioDTO comentarioDTO = PojoUtils.copyProperties(comentario, ComentarioDTO.class);
			return comentarioDTO;
		};
	}

	
	
}
