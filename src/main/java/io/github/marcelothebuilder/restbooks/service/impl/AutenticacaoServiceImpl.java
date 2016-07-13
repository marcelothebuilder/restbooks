package io.github.marcelothebuilder.restbooks.service.impl;

import org.jooq.tools.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import io.github.marcelothebuilder.restbooks.service.AutenticacaoService;

@Service
public class AutenticacaoServiceImpl implements AutenticacaoService {
	@Override
	public String getNomeUsuario() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = StringUtils.toCamelCase(authentication.getName());
		return name;
	}

}
