package br.unb.cic.iris.gui.screen;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = -6238186065495538006L;
	private JPanel northPanel;
	private JPanel centerPanel;
	private JPanel statusPanel;
	
	private JToolBar toolBar;
	//private JTextField statusTextField;
	private JTextArea statusTextArea;

	public MainFrame(){
		super();	
		initLayout();
	}
	
	private void initLayout(){
		setLayout(new BorderLayout());
		
		toolBar = new JToolBar("Commands");	
		northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());
		northPanel.add(toolBar, CENTER);
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		
		statusTextArea = new JTextArea();		
		statusTextArea.setEditable(false);
		statusTextArea.setRows(6);
		statusPanel = new JPanel();
		statusPanel.setLayout(new BorderLayout());
		statusPanel.add(new JScrollPane(statusTextArea), CENTER);
		
		getContentPane().add(northPanel, NORTH);
		getContentPane().add(centerPanel, CENTER);
		getContentPane().add(statusPanel, SOUTH);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setSize(800, 600);		
	}

	public JPanel getCenterPanel() {
		return centerPanel;
	}

	public JToolBar getToolBar() {
		return toolBar;
	}

	public JTextArea getStatusTextArea() {
		return statusTextArea;
	}

	
}
