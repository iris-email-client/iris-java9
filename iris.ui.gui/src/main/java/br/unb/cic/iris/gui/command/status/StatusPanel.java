package br.unb.cic.iris.gui.command.status;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class StatusPanel extends JPanel {
	private static final long serialVersionUID = -8191286754725706587L;
	private JTextArea textArea;
	
	public StatusPanel(){
		textArea = new JTextArea();
		
		setLayout(new BorderLayout());
		add(new JScrollPane(textArea), BorderLayout.CENTER);
	}
	
	public void update(String text){
		textArea.setText(text);
	}
	
}
