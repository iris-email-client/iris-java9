/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unb.cic.iris.gui.command.send;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.unb.cic.iris.core.IrisServiceLocator;
import br.unb.cic.iris.core.SystemFacade;
import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.exception.IrisUncheckedException;
import br.unb.cic.iris.gui.GuiManager;
import br.unb.cic.iris.mail.EmailStatusListener;
import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.model.Status;

/**
 *
 * @author pedro
 */
public class SendPanel extends JPanel implements EmailStatusListener {
	private static final long serialVersionUID = 2648101003972409009L;
	private static final int COLS = 40;
	private String from; 

	/**
	 * Creates new form SendPanel
	 */
	public SendPanel() {
		if (checkProviderDefinition()) {
			initComponents();
		}
	}

	private boolean checkProviderDefinition() {
		boolean isProviderDefined = false;
		if (SystemFacade.instance().getStatus() == Status.NOT_CONNECTED) {
			GuiManager.instance().showErrorMessage("No email provider defined");
		} else {
			from = SystemFacade.instance().getProvider().getUsername();
			isProviderDefined = true;
		}
		return isProviderDefined;
	}

	private void initComponents() {
		setBorder(BorderFactory.createTitledBorder(""));

		jPanelHeader = new JPanel();
		jPanelBody = new JPanel();
		jScrollPane1 = new JScrollPane();
		jPanelButton = new JPanel();
		jButtonSend = new JButton();

		setLayout(new BorderLayout());

		jPanelHeader.setLayout(new GridBagLayout());

		jTextFieldFrom = new JTextField(COLS);
		jTextFieldFrom.setEditable(false);
		jTextFieldFrom.setText(from); // TODO
		jPanelHeader.add(new JLabel("From"), createGridBagConstraints(0, 0));
		jPanelHeader.add(jTextFieldFrom, createGridBagConstraints(1, 0));

		jTextFieldTo = new JTextField(COLS);
		jPanelHeader.add(new JLabel("To"), createGridBagConstraints(0, 1));
		jPanelHeader.add(jTextFieldTo, createGridBagConstraints(1, 1));

		jTextFieldCc = new JTextField(COLS);
		jPanelHeader.add(new JLabel("Cc"), createGridBagConstraints(0, 2));
		jPanelHeader.add(jTextFieldCc, createGridBagConstraints(1, 2));

		jTextFieldBcc = new JTextField(COLS);
		jPanelHeader.add(new JLabel("Bcc"), createGridBagConstraints(0, 3));
		jPanelHeader.add(jTextFieldBcc, createGridBagConstraints(1, 3));

		jTextFieldSubject = new JTextField(COLS);
		jPanelHeader.add(new JLabel("Subject"), createGridBagConstraints(0, 4));
		jPanelHeader.add(jTextFieldSubject, createGridBagConstraints(1, 4));

		add(jPanelHeader, BorderLayout.NORTH);

		jPanelBody.setBorder(BorderFactory.createTitledBorder("Message"));
		jPanelBody.setLayout(new BorderLayout());

		jTextAreaMessage = new JTextArea(20, 5);
		jScrollPane1.setViewportView(jTextAreaMessage);

		jPanelBody.add(jScrollPane1, BorderLayout.CENTER);

		jButtonSend.setText("     Send     ");
		jButtonSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonSendActionPerformed(evt);
			}
		});
		jPanelButton.add(jButtonSend);

		jPanelBody.add(jPanelButton, BorderLayout.PAGE_END);

		add(jPanelBody, BorderLayout.CENTER);
	}

	private void jButtonSendActionPerformed(ActionEvent evt) {
		final SendPanel panel = this;
		EmailMessage m = createMessage();
		
		new Thread(new Runnable() {
			public void run() {
				try {
					SystemFacade.instance().send(m, panel);
				} catch (IrisException e) {
					//GuiManager.instance().showErrorMessage("Error while sending message: "+e.getMessage());
					throw new IrisUncheckedException("Error while sending message: "+e.getMessage(), e);
				}
			}
		}).start();	
	}

	private EmailMessage createMessage() {
		String to = getText(jTextFieldTo);
		String cc = getText(jTextFieldCc);
		String bcc = getText(jTextFieldBcc);
		String subject = getText(jTextFieldSubject);
		String content = getMessage();
		return IrisServiceLocator.instance().getEntityFactory().createEmailMessage(from, to, cc, bcc, subject, content);
	}

	private String getText(JTextField textField) {
		return textField.getText().trim();
	}

	public String getMessage() {
		return jTextAreaMessage.getText().trim();
	}

	private GridBagConstraints createGridBagConstraints(int x, int y) {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = x;
		gridBagConstraints.gridy = y;
		return gridBagConstraints;
	}

	//TODO use swingworker ... but not in the scope of this prototype
	@Override
	public void statusChanged(String message) {
		GuiManager.instance().appendStatusText(message);
	}

	@Override
	public void notifyMessageDownloadProgress(int value) {
		// do nothing
	}
	
	
	private JTextField jTextFieldFrom;
	private JTextField jTextFieldTo;
	private JTextField jTextFieldCc;
	private JTextField jTextFieldBcc;

	private JButton jButtonSend;
	private JPanel jPanelBody;
	private JPanel jPanelButton;
	private JPanel jPanelHeader;
	private JScrollPane jScrollPane1;
	private JTextArea jTextAreaMessage;
	private JTextField jTextFieldSubject;

}
