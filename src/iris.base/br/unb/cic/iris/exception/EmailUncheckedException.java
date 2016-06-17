package br.unb.cic.iris.exception;

/***
 * added by dBaseException
 */
public class EmailUncheckedException extends RuntimeException {
	private static final long serialVersionUID = -2841936434227540649L;

	public EmailUncheckedException() {
		super();
	}

	public EmailUncheckedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EmailUncheckedException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmailUncheckedException(String message) {
		super(message);
	}

	public EmailUncheckedException(Throwable cause) {
		super(cause);
	}
}