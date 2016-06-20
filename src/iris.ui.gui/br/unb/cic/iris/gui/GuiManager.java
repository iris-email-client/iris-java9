package br.unb.cic.iris.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import br.unb.cic.iris.gui.screen.MainFrame;

public class GuiManager {
	private static GuiManager instance = new GuiManager();

	private MainFrame frame;

	private GuiManager() {
		frame = new MainFrame();
		frame.setVisible(true);
	}

	public static GuiManager instance() {
		return instance;
	}

	public void addToolbarComponent(Component component) {
		frame.getToolBar().add(component);
	}

	public void setCenterPanel(JPanel panel) {
		frame.getCenterPanel().removeAll();
		frame.getCenterPanel().add(panel, BorderLayout.CENTER);
		frame.revalidate();
		// frame.repaint();

		// revalidate tells the layout manager to reset based on the new
		// component list. This will also trigger a call to repaint.
		// repaint is used to tell a component to repaint itself.
	}

	public void setStatusText(String txt) {
		frame.getStatusTextArea().setText(txt);
	}
	
	public void appendStatusText(String txt){
		frame.getStatusTextArea().append("\n"+txt);
	}

	public void showErrorMessage(String msg) {
		showMessage(msg, "ERROR", JOptionPane.ERROR_MESSAGE);
	}

	public void showInfoMessage(String msg) {
		showMessage(msg, "INFO", JOptionPane.INFORMATION_MESSAGE);
	}

	public void showMessage(String message, String title, int type) {
		JOptionPane.showMessageDialog(frame, message, title, type);
	}

}
