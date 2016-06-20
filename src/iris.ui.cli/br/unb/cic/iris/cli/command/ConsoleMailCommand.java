package br.unb.cic.iris.cli.command;

import br.unb.cic.iris.command.MailCommand;

public interface ConsoleMailCommand extends MailCommand {	
	public void setParameters(java.util.List<String> parameters);
}
