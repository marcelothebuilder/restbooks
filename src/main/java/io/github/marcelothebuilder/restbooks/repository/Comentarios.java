package io.github.marcelothebuilder.restbooks.repository;

import org.springframework.stereotype.Repository;

import io.github.marcelothebuilder.restbooks.domain.Comentario;

@Repository
public interface Comentarios {
	Comentario salvar(Comentario comentario);
}
