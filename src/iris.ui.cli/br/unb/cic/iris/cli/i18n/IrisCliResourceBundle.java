package br.unb.cic.iris.cli.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;



public class IrisCliResourceBundle extends PropertyResourceBundle {

	public IrisCliResourceBundle(InputStream input) throws IOException {
		super(input);		
		setParent(br.unb.cic.iris.core.i18n.MessageBundle.instance().getBundle());
	}

}
