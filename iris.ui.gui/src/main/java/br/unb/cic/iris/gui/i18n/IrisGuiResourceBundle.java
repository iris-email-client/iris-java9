package br.unb.cic.iris.gui.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;



public class IrisGuiResourceBundle extends PropertyResourceBundle {

	public IrisGuiResourceBundle(InputStream input) throws IOException {
		super(input);		
		setParent(br.unb.cic.iris.core.i18n.MessageBundle.instance().getBundle());
	}

}
