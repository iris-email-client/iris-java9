package br.unb.cic.iris.gui.command.send;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Teste extends JFrame {

	public static void main(String[] args) {
		Teste frame = new Teste();
		frame.setVisible(true);
	}

	public Teste() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);

		SendPanel panel = new SendPanel();

		contentPane.add(panel, BorderLayout.CENTER);
	}

}