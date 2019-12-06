package br.unb.cic.iris.gui.command.download;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import br.unb.cic.iris.core.SystemFacade;
import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.exception.IrisUncheckedException;
import br.unb.cic.iris.gui.GuiManager;
import br.unb.cic.iris.gui.screen.IrisFolderCellRenderer;
import br.unb.cic.iris.gui.screen.IrisFolderComboBoxModel;
import br.unb.cic.iris.mail.EmailStatusListener;
import br.unb.cic.iris.mail.EmailStatusManager;
import br.unb.cic.iris.model.IrisFolder;

public class DownloadPanel extends JPanel implements PropertyChangeListener {
		
	private IrisFolderComboBoxModel model;
	private JPanel progressPanel;
	private final JProgressBar progressBar = new JProgressBar();
	private JButton btnDownloadMessages;
	
	public DownloadPanel(){
		initComponents();
	}

	@SuppressWarnings("unchecked")
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
		model = new IrisFolderComboBoxModel();
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
				
		progressBar.setStringPainted(true);
		progressPanel.add(progressBar);
		
		btnDownloadMessages.setEnabled(false);
		progressPanel.setVisible(false);
	}

	
	private void downloadMessages() {						
		IrisFolder folder = (IrisFolder) model.getSelectedItem();	
			
		worker worker = new worker(folder.getName().toUpperCase());
		worker.addPropertyChangeListener(this);
		worker.execute();
	}

	public void setFolders(List<IrisFolder> folders){
		btnDownloadMessages.setEnabled(true);
		progressPanel.setVisible(true);
		model.setFolders(folders);
	}
	
/*	@Override
	public void statusChanged(String message) {
		GuiManager.instance().appendStatusText(message);
	}

	@Override
	public void notifyMessageDownloadProgress(int value) {
		System.err.println("notifyMessageDownloadProgress: "+value);
		//progressBar.setValue(value);
		//progressBar.repaint();
		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	progressBar.setValue(value);
		    }
		});
	}*/

	
	class worker extends SwingWorker<Void, Void> implements EmailStatusListener{
		private String folder;

		public worker(String folder){
			this.folder = folder;
			EmailStatusManager.instance().setListener(this);
			
		}

		@Override
		public void statusChanged(String message) {
			//System.out.println("status: "+message);
			GuiManager.instance().appendStatusText(message);
		}

		@Override
		public void notifyMessageDownloadProgress(int value) {
			//System.err.println("worker.notifyMessageDownloadProgress: "+value);
			setProgress(value);
		}
		@Override
		protected Void doInBackground() throws Exception {
			try {
				SystemFacade.instance().downloadMessages(folder);
			} catch (IrisException e) {
				throw new IrisUncheckedException("Error while downloading messages: "+e.getMessage(), e);
			}
			return null;
		}
		
	}
	public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
            GuiManager.instance().appendStatusText(String.format("Completed %d%% of task.", progress));
        } 
    }

}
