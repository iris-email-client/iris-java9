package br.unb.cic.iris.cli.command.internal;

import java.util.Scanner;

import br.unb.cic.iris.cli.command.ConsoleAbstractMailCommand;
import br.unb.cic.iris.core.SystemFacade;
import br.unb.cic.iris.exception.EmailException;
import br.unb.cic.iris.exception.EmailUncheckedException;
import br.unb.cic.iris.mail.EmailStatusListener;
import br.unb.cic.iris.model.EmailMessage;

/***
 * added by dConsole
 */
public class ConsoleSendMessageCommand extends ConsoleAbstractMailCommand implements EmailStatusListener {
	public static final String COMMAND_SEND = "send";		

	@Override
	public String explain() {
		return String.format("(%s) - %s %n", COMMAND_SEND, message("command.send.explain"));
	}

	@Override
	public void handleExecute() throws EmailException {
		if(SystemFacade.instance().getProvider() == null){
			throw new EmailUncheckedException(message("error.no.provider"));
		}
		
		EmailMessage m = createMessage();
		SystemFacade.instance().send(m, this);
	}

	private EmailMessage createMessage() {
		Scanner sc = new Scanner(System.in);
		String from = SystemFacade.instance().getProvider().getUsername();
		System.out.printf("%s: %s%n", message("command.send.label.from"), from);
		String to = read(sc, "command.send.label.to");
		String cc = read(sc, "command.send.label.cc");
		String bcc = read(sc, "command.send.label.bcc");
		String subject = read(sc, "command.send.label.subject");
		String content = read(sc, "command.send.label.content");
		return SystemFacade.instance().getEntityFactory().createEmailMessage(from, to, cc, bcc, subject, content);		
	}

	private static String read(Scanner sc, String question) {
		askQuestion(question);
		return sc.nextLine();
	}

	private static void askQuestion(String message) {
		System.out.printf("%s: ", message(message));
	}

	@Override
	public String getCommandName() {
		return COMMAND_SEND;
	}

	
	@Override
	public void statusChanged(String message) {
		System.out.println(message);
	}

	@Override
	public void notifyMessageDownloadProgress(int value) {
		//do nothing
	}

}