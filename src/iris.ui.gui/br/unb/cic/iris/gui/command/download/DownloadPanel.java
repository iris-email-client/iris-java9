package br.unb.cic.iris.gui.command.download;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import br.unb.cic.iris.core.SystemFacade;
import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.exception.IrisUncheckedException;
import br.unb.cic.iris.gui.GuiManager;
import br.unb.cic.iris.mail.EmailStatusListener;
import br.unb.cic.iris.model.IrisFolder;

public class DownloadPanel extends JPanel implements EmailStatusListener {
		
	private DownloadComboBoxModel model;
	private JPanel progressPanel;
	private JProgressBar progressBar;
	private JButton btnDownloadMessages;
	
	public DownloadPanel(){
		initComponents();
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		
		
		JPanel northPanel = new JPanel();
		northPanel.setBorder(new TitledBorder(null, "Select Folder", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel selectFoldersPanel = new JPanel();
		northPanel.add(selectFoldersPanel);
		
		JLabel lblNewLabel = new JLabel("Remote Folder");
		selectFoldersPanel.add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		model = new DownloadComboBoxModel();
		comboBox.setModel(model);
		comboBox.setRenderer(new IrisFolderCellRenderer());
		selectFoldersPanel.add(comboBox);
		
		JPanel downloadMessagesPanel = new JPanel();
		northPanel.add(downloadMessagesPanel);
		
		btnDownloadMessages = new JButton("Download Messages");		
		btnDownloadMessages.addActionListener(al -> {
			downloadMessages();
		});
		downloadMessagesPanel.add(btnDownloadMessages);
		
		JPanel centerPanel = new JPanel();
		add(centerPanel, BorderLayout.CENTER);
		
		progressPanel = new JPanel();
		progressPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		centerPanel.add(progressPanel);
		progressPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("               PROGRESS               ");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		progressPanel.add(lblNewLabel_1);
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressPanel.add(progressBar);
		
		btnDownloadMessages.setEnabled(false);
		progressPanel.setVisible(false);
	}

	
	private void downloadMessages() {		
		progressPanel.setVisible(true);
		IrisFolder folder = (IrisFolder) model.getSelectedItem();			
		Runnable runnable = () -> {
			try {
				SystemFacade.instance().downloadMessages(folder.getName().toUpperCase());
			} catch (IrisException e) {
				throw new IrisUncheckedException("Error while downloading messages: "+e.getMessage(), e);
			}
		};
		runnable.run();
	}

	public void setFolders(List<IrisFolder> folders){
		btnDownloadMessages.setEnabled(true);
		model.setFolders(folders);
	}
	
	@Override
	public void statusChanged(String message) {
		GuiManager.instance().appendStatusText(message);
	}

	@Override
	public void notifyMessageDownloadProgress(int value) {
		progressBar.setValue(value);
	}

}
