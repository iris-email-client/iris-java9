package br.unb.cic.iris.persistence;

import br.unb.cic.iris.exception.EmailException;

public class PersistenceException extends EmailException {
	private static final long serialVersionUID = 4058033415287561509L;

	public PersistenceException(String message, Exception error) {
		super(message, error);
	}

	public PersistenceException(String message) {
		super(message);
	}

}
