package br.unb.cic.iris.gui;

import static br.unb.cic.iris.i18n.MessageBundle.message;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.exception.IrisUncheckedException;
import br.unb.cic.iris.exception.IrisValidationException;
import br.unb.cic.iris.gui.screen.MainFrame;
import br.unb.cic.iris.persistence.IrisPersistenceException;

public class GuiManager {
	private static GuiManager instance = new GuiManager();

	private MainFrame frame;

	private GuiManager() {
		frame = new MainFrame();
		frame.setTitle(message("title"));
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

	public void appendStatusText(String txt) {
		frame.getStatusTextArea().append("\n" + txt);
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

	public void showException(IrisUncheckedException e) {
		System.out.println("IrisUncheckedException");
		showException((Exception) e);
	}

	public void showException(IrisValidationException e) {
		System.out.println("IrisValidationException");
		StringBuilder sb = new StringBuilder("Validation Error: ");
		for (String msg : e.getMessages()) {
			sb.append("\n - " + msg);
		}
		System.err.println(sb.toString());
		e.printStackTrace();
		showErrorMessage(sb.toString());
	}

	public void showException(IrisPersistenceException e) {
		System.out.println("PersistenceException");
		showException((Exception) e);
	}

	public void showException(IrisException e) {
		System.out.println("IrisException");
		showException((Exception) e);		
	}

	public void showException(Exception e) {
		System.out.println("Exception");
		e.printStackTrace();
		showErrorMessage(String.format("%s: %s\n", message("error"), e.getLocalizedMessage()));
	}

	public void showException(Throwable e) {
		System.out.println("Throwable");
		e.printStackTrace();
	}

}
