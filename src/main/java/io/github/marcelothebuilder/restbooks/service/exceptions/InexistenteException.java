package io.github.marcelothebuilder.restbooks.service.exceptions;

public abstract class InexistenteException extends Exception {
	private static final long serialVersionUID = 1L;

	public InexistenteException() {
	}

	public InexistenteException(String message) {
		super(message);
	}

	public InexistenteException(Throwable cause) {
		super(cause);
	}

	public InexistenteException(String message, Throwable cause) {
		super(message, cause);
	}

	public InexistenteException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
