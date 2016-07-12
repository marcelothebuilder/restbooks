package io.github.marcelothebuilder.restbooks.service.exceptions;

public class AutorInexistenteException extends InexistenteException {
	private static final long serialVersionUID = 1L;

	public AutorInexistenteException() {
	}

	public AutorInexistenteException(String message) {
		super(message);
	}

	public AutorInexistenteException(Throwable cause) {
		super(cause);
	}

	public AutorInexistenteException(String message, Throwable cause) {
		super(message, cause);
	}

	public AutorInexistenteException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public AutorInexistenteException(Long codigo) {
		super(String.format("Autor de código %d não existe", codigo));
	}

}
