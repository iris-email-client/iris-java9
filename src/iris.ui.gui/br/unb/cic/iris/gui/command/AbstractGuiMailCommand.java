package br.unb.cic.iris.gui.command;

import java.awt.Image;

import javax.swing.ImageIcon;

import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.exception.IrisUncheckedException;
import br.unb.cic.iris.exception.IrisValidationException;
import br.unb.cic.iris.gui.GuiManager;
import br.unb.cic.iris.i18n.MessageBundle;
import br.unb.cic.iris.persistence.IrisPersistenceException;

public abstract class AbstractGuiMailCommand implements GuiMailCommand {

	protected abstract void handleExecute() throws IrisException;

	// TODO i18n ...
	public void execute() {	
		try {
			handleExecute();
		}  catch (IrisUncheckedException iue) {
			GuiManager.instance().showException(iue);			
		} catch (IrisValidationException ive) {
			GuiManager.instance().showException(ive);
		} catch (IrisPersistenceException pe) {
			GuiManager.instance().showException(pe);
		} catch (IrisException ex) {
			GuiManager.instance().showException(ex);
		} catch (Exception e) {
			GuiManager.instance().showException(e);
		} catch (Throwable t) {
			GuiManager.instance().showException(t);
		}
	}

	protected String message(String key) {
		return MessageBundle.message(key);
	}

	protected void showErrorMessage(String message) {
		GuiManager.instance().showErrorMessage(message);
	}

	protected void showInfoMessage(String message) {
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