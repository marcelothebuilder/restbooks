package io.github.marcelothebuilder.restbooks.service;

import java.util.List;

import io.github.marcelothebuilder.restbooks.dto.AutorDTO;
import io.github.marcelothebuilder.restbooks.service.exceptions.AutorInexistenteException;

public interface AutoresService {
	AutorDTO salvar(AutorDTO autor);
	List<AutorDTO> listar();
	AutorDTO buscar(Long codigo) throws AutorInexistenteException;
}
