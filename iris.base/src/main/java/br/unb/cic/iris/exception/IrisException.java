package br.unb.cic.iris.exception;

/***
 * added by dBaseException
 */
public class IrisException extends Exception {
	private static final long serialVersionUID = -6229420404810056559L;

	public IrisException(String message) {
		super(message);
	}

	public IrisException(String message, Exception error) {
		super(message, error);
	}
}