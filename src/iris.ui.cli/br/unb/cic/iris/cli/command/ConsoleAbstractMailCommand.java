package br.unb.cic.iris.cli.command;

import java.util.List;

import br.unb.cic.iris.command.AbstractMailCommand;

public abstract class ConsoleAbstractMailCommand extends AbstractMailCommand {
	protected List<String> parameters;


	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

	protected boolean validParameters() {
		return parameters != null && parameters.size() > 0;
	}

}
