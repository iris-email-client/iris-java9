package br.unb.cic.iris.command;

import java.util.Comparator;

/***
 * added by dBaseCommand
 */
public interface MailCommand {
	public static final Comparator<MailCommand> COMPARE_BY_NAME = (MailCommand c1, MailCommand c2) -> c1.getCommandName().compareTo(c2.getCommandName());
	
	
	public void execute();

	public String explain();

	public String getCommandName();
}