package io.github.marcelothebuilder.restbooks.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.marcelothebuilder.restbooks.dto.AutorDTO;
import io.github.marcelothebuilder.restbooks.model.Autor;
import io.github.marcelothebuilder.restbooks.repository.Autores;
import io.github.marcelothebuilder.restbooks.service.AbstractAutoresService;
import io.github.marcelothebuilder.restbooks.service.exceptions.AutorInexistenteException;

@Service
public class AutoresServiceImpl extends AbstractAutoresService {

	@Autowired
	private Autores autores;

	@Override
	public List<AutorDTO> listar() {
		return autores.todos().stream()
			.map(AutoresServiceImpl::toDto)
			.collect(Collectors.toList());
	}

	@Override
	public AutorDTO salvar(AutorDTO autorDTO) {
		autorDTO.setCodigo(null);
		Autor autor = autores.salvar(fromDto(autorDTO));
		return toDto(autor);
	}

	@Override
	public AutorDTO buscar(Long codigo) throws AutorInexistenteException {	
		Autor autor = autores.buscar(codigo);
		if (autor == null) {
			throw new AutorInexistenteException(codigo);
		}
		return toDto(autor);
	}

}
