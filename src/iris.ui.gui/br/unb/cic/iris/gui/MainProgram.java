package br.unb.cic.iris.gui;

import static br.unb.cic.iris.i18n.MessageBundle.message;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import br.unb.cic.iris.gui.screen.MainPanel;

public class MainProgram {

	public static void main(String[] args) {
		System.out.println("Starting app ...");
		/* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {				
				MainProgram gui = new MainProgram();
				gui.show();
			}
		});
	}

	void show() {
		// 1. Create the frame.
		JFrame frame = new JFrame(message("title"));

		// 2. Optional: What happens when the frame closes?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 3. Create components and put them in the frame.
		frame.getContentPane().add(new MainPanel(), BorderLayout.CENTER);

		// 4. Size the frame.
		frame.pack();

		// 5. Show it.
		frame.setVisible(true);
	}

}
