package br.unb.cic.iris.cli.command;

import java.util.List;

import br.unb.cic.iris.command.AbstractMailCommand;

//TODO rever hierarquia (AbstractMailCommand implementa MailCommand)
public abstract class ConsoleAbstractMailCommand extends AbstractMailCommand implements ConsoleMailCommand {
	protected List<String> parameters;


	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

	protected boolean validParameters() {
		return parameters != null && parameters.size() > 0;
	}

}
