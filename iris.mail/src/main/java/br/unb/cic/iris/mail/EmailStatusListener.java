package br.unb.cic.iris.mail;

public interface EmailStatusListener {

	public void statusChanged(String message);	
	public void notifyMessageDownloadProgress(int value); //in %
	
}
