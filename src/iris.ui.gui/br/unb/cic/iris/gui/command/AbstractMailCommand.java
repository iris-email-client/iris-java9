package br.unb.cic.iris.gui.command;

import java.awt.Image;

import javax.swing.ImageIcon;

import br.unb.cic.iris.exception.EmailException;
import br.unb.cic.iris.exception.EmailMessageValidationException;
import br.unb.cic.iris.exception.EmailUncheckedException;
import br.unb.cic.iris.gui.GuiManager;
import br.unb.cic.iris.i18n.MessageBundle;

/***
 * added by dBaseCommand
 */
public abstract class AbstractMailCommand implements GuiMailCommand {

	protected abstract void handleExecute() throws EmailException;

	//TODO i18n ...
	public void execute() {
		try {
			handleExecute();
		} catch (EmailUncheckedException eux) {
			eux.printStackTrace();
			showErrorMessage(String.format("%s: %s","ERROR", eux.getLocalizedMessage()));			
		} catch (EmailMessageValidationException emvx) {			
			StringBuilder sb = new StringBuilder("Erro validacao: ");			
			for (String msg : emvx.getMessages()) {
				sb.append("\n - " + msg);
			}
			System.err.println(sb.toString());
			emvx.printStackTrace();
			showErrorMessage(sb.toString());
		} catch (EmailException ex) {
			showErrorMessage(String.format("%s: %s\n", "ERROR", ex.getMessage()));
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	protected String message(String key) {
		return MessageBundle.message(key);
	}
	
	protected void showErrorMessage(String message){
		GuiManager.instance().showErrorMessage(message);
	}
	
	protected void showInfoMessage(String message){
		GuiManager.instance().showInfoMessage(message);
	}
		
	protected ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = getClass().getResource(path);		
		if (imgURL != null) {			
			Image img = new ImageIcon(imgURL, description).getImage();
			ImageIcon icon = new ImageIcon(img.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH));
			return icon;
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
}