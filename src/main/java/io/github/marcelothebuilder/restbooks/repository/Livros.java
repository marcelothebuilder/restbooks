package io.github.marcelothebuilder.restbooks.repository;

import java.util.List;

import io.github.marcelothebuilder.restbooks.model.Livro;

public interface Livros {
	List<Livro> todos();

	Livro salvar(Livro livro);

	Livro buscar(Long codigo);

	void deletar(Long codigo);
}
