package br.unb.cic.iris.exception;

/***
 * added by dBaseException
 */
public class IrisUncheckedException extends RuntimeException {
	private static final long serialVersionUID = -2841936434227540649L;

	public IrisUncheckedException() {
		super();
	}

	public IrisUncheckedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IrisUncheckedException(String message, Throwable cause) {
		super(message, cause);
	}

	public IrisUncheckedException(String message) {
		super(message);
	}

	public IrisUncheckedException(Throwable cause) {
		super(cause);
	}
}