package io.github.marcelothebuilder.restbooks.service;

import java.util.List;

import io.github.marcelothebuilder.restbooks.dto.AutorDTO;

public interface AutoresService {
	AutorDTO salvar(AutorDTO autor);
	List<AutorDTO> listar();
}
