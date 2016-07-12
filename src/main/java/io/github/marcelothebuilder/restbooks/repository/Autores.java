package io.github.marcelothebuilder.restbooks.repository;

import java.util.List;

import io.github.marcelothebuilder.restbooks.model.Autor;

public interface Autores {
	List<Autor> todos();

	Autor salvar(Autor autor);

	Autor buscar(Long codigo);
}
