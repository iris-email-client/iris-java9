package br.unb.cic.iris.command;

/***
 * added by dBaseCommand
 */
public interface MailCommand {
	public void execute();

	//TODO apenas para CLI
	public void setParameters(java.util.List<String> parameters);

	public String explain();

	public String getCommandName();
}