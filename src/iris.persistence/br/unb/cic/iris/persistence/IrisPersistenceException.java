package br.unb.cic.iris.persistence;

import br.unb.cic.iris.exception.IrisException;

public class IrisPersistenceException extends IrisException {
	private static final long serialVersionUID = 4058033415287561509L;

	public IrisPersistenceException(String message, Exception error) {
		super(message, error);
	}

	public IrisPersistenceException(String message) {
		super(message);
	}

}
