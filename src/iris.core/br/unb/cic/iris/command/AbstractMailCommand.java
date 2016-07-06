package br.unb.cic.iris.command;

import br.unb.cic.iris.core.i18n.MessageBundle;
import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.exception.IrisValidationException;
import br.unb.cic.iris.exception.IrisUncheckedException;

/***
 * added by dBaseCommand
 */
public abstract class AbstractMailCommand implements MailCommand {	

	public static String message(String key) {
		return MessageBundle.message(key);
	}

	protected abstract void handleExecute() throws IrisException;

	public void execute() {
		try {
			handleExecute();
		} catch (IrisUncheckedException eux) {
			System.err.printf("%s: %s\n", MessageBundle.message("error"), eux.getLocalizedMessage());
		} catch (IrisValidationException emvx) {
			System.err.println(MessageBundle.message("error.validation"));
			for (String msg : emvx.getMessages()) {
				System.err.println(" - " + msg);
			}
		} catch (IrisException ex) {
			System.err.printf("%s: %s\n", MessageBundle.message("error"), ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}