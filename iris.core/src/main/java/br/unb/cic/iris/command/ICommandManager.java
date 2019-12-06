package br.unb.cic.iris.command;

import java.util.List;

import br.unb.cic.iris.exception.IrisException;

/***
 * added by dBaseCommand
 */
public interface ICommandManager {
	public void addCommand(MailCommand command) throws IrisException;

	public MailCommand getCommand(String commandName) throws IrisException;

	public List<MailCommand> listAll();

	public void addCommandListener(CommandListener listener);

	public void reload() throws Exception;
}