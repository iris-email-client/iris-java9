package br.unb.cic.iris.gui.command;


import java.awt.event.ActionEvent;
import java.util.List;

import br.unb.cic.iris.core.SystemFacade;
import br.unb.cic.iris.exception.EmailException;
import br.unb.cic.iris.gui.GuiManager;
import br.unb.cic.iris.gui.screen.SendPanel;
import br.unb.cic.iris.model.EmailMessage;


public class GuiSendMesssageCommand extends AbstractGuiMailCommand {
	private SendPanel panel;

	public GuiSendMesssageCommand(){
		panel = new SendPanel();		
	}
	
	@Override
	public String explain() {
		return "Send ...";
	}
	
	protected EmailMessage createMessage() {
		String to, subject, content;
		
		to = panel.getTo();
		subject = panel.getSubject();
		content = panel.getContent();

		EmailMessage message = SystemFacade.instance().getEntityFactory().createEmailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setMessage(content);
		
		return message;
	}

	@Override
	public String getCommandName() {
		return "SendMessageGUI";
	}

	public void setPanel(SendPanel panel) {
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		panel.showInDialog();
	}

	@Override
	protected void handleExecute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void register(GuiManager manager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParameters(List<String> parameters) {
		// TODO Auto-generated method stub
		
	}

}
