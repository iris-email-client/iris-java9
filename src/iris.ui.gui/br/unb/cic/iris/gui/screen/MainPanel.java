/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unb.cic.iris.gui.screen;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;

import br.unb.cic.iris.gui.command.GuiCommand;
import br.unb.cic.iris.gui.command.GuiCommandManager;

/**
 *
 * @author pedro
 */
public class MainPanel extends javax.swing.JPanel {

	/**
	 * Creates new form PainelPrincipal
	 */
	public MainPanel() {
		jPanelNorth = new javax.swing.JPanel();
		GridLayout layout = new GridLayout(0,1);
		jPanelNorth.setLayout(layout);
		layout.setHgap(2);
        layout.setVgap(5);

		setLayout(new java.awt.BorderLayout());

		add(jPanelNorth, java.awt.BorderLayout.CENTER);

		initCommands();
	}

	@SuppressWarnings("unchecked")
	public void initCommands() {
		List<GuiCommand> commands = GuiCommandManager.singleton().listGuiCommands();
		for (GuiCommand command : commands) {
			JButton btn = new JButton(command.getCommandName());
			btn.addActionListener(command);
			jPanelNorth.add(btn);
		}

	}

	private javax.swing.JPanel jPanelNorth;
}
