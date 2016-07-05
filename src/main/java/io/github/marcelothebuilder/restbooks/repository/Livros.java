package io.github.marcelothebuilder.restbooks.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import io.github.marcelothebuilder.restbooks.domain.Livro;

@Repository
public interface Livros {
	List<Livro> todos();
	Livro salvar(Livro livro);
	Livro buscar(Long codigo);
	void deletar(Long codigo);
}
