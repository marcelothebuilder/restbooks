package io.github.marcelothebuilder.restbooks.service;

import java.util.List;

import io.github.marcelothebuilder.restbooks.dto.ComentarioDTO;
import io.github.marcelothebuilder.restbooks.dto.LivroDTO;
import io.github.marcelothebuilder.restbooks.service.exceptions.LivroInexistenteException;

public interface LivrosService {

	List<LivroDTO> listar();

	LivroDTO buscar(Long codigo) throws LivroInexistenteException;

	LivroDTO salvar(LivroDTO livro);

	void deletar(Long codigo) throws LivroInexistenteException;

	LivroDTO atualizar(LivroDTO livro) throws LivroInexistenteException;

	ComentarioDTO salvarComentario(Long codigoLivro, ComentarioDTO comentario) throws LivroInexistenteException;

	List<ComentarioDTO> listarComentarios(Long codigoLivro) throws LivroInexistenteException;

}