package br.unb.cic.iris.command;

/***
 * added by dBaseCommand
 */
public class CommandNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CommandNotFoundException() {
	}

	public CommandNotFoundException(String msg) {
		super(msg);
	}

	public CommandNotFoundException(Throwable cause) {
		super(cause);
	}
}