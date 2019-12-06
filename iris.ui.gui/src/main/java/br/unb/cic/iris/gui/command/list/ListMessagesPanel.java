package br.unb.cic.iris.gui.command.list;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import br.unb.cic.iris.core.FolderManager;
import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.gui.screen.IrisFolderCellRenderer;
import br.unb.cic.iris.gui.screen.IrisFolderComboBoxModel;
import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.model.IrisFolder;

public class ListMessagesPanel extends JPanel {
	private IrisFolderComboBoxModel comboBoxModel;		
	private ListMessagesTableModel tableModel;
	private JButton btnListMessages;
	private JEditorPane editorPane;
	
	public ListMessagesPanel(){
		initComponents();
		initData();
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
		
		JLabel lblNewLabel = new JLabel("Local Folder");
		selectFoldersPanel.add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBoxModel = new IrisFolderComboBoxModel();
		comboBox.setModel(comboBoxModel);
		comboBox.setRenderer(new IrisFolderCellRenderer());
		selectFoldersPanel.add(comboBox);
		
		JPanel showMessagesBtnPanel = new JPanel();
		northPanel.add(showMessagesBtnPanel);
		
		btnListMessages = new JButton("List Messages");		
		btnListMessages.addActionListener(al -> {
			listMessages();
		});
		showMessagesBtnPanel.add(btnListMessages);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		add(centerPanel, BorderLayout.CENTER);
		
		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		split.setOneTouchExpandable(true);
		split.setDividerLocation(150);
		
		tableModel = new ListMessagesTableModel();
		JTable table = new JTable(tableModel);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				EmailMessage message = tableModel.getMessage(row);
				//System.out.println("########### MESSAGE: "+message);
				editorPane.setText(populateTemplate(message));
			}
		});
		split.add(new JScrollPane(table));
		
		editorPane = new JEditorPane();
		editorPane.setContentType("text/html");
		split.add(new JScrollPane(editorPane));
		
		centerPanel.add(split, BorderLayout.CENTER);
				
		btnListMessages.setEnabled(false);		
	}

	
	private void listMessages() {						
		IrisFolder folder = (IrisFolder) comboBoxModel.getSelectedItem();
		try {
			FolderManager.instance().changeToFolder(folder.getName());
			List<EmailMessage> messages = FolderManager.instance().listFolderMessages();
			tableModel.setMessages(messages);
			editorPane.setText("");
		} catch (IrisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initData() {
	try {
		List<IrisFolder> folders = FolderManager.instance().listFolders();
		setFolders(folders);		
	} catch (IrisException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}

	public void setFolders(List<IrisFolder> folders){
		btnListMessages.setEnabled(true);		
		comboBoxModel.setFolders(folders);
	}
	
	private String populateTemplate(EmailMessage msg){
		return String.format(TEMPLATE
				, ListMessagesUtil.parseEmail(msg.getFrom())
				, ListMessagesUtil.parseDateFull(msg.getDate())
				, ListMessagesUtil.parseEmail(msg.getTo())
				, ListMessagesUtil.parseEmail(msg.getCc())
				, ListMessagesUtil.parseEmail(msg.getBcc())
				, msg.getSubject()
				, msg.getMessage());
	}
	
	private static final String TEMPLATE = 
			"<b>FROM: </b> %s <br>"+
			"<b>DATE: </b> %s <br>"+
			"<b>TO: </b> %s <br>"+
			"<b>CC: </b> %s <br>"+
			"<b>BCC: </b> %s <br>"+				
			"<b>SUBJECT: </b> %s <br>"+
			"<b>CONTENT: </b> <p> %s </p>";
}
