package br.unb.cic.iris.mail;

public class EmailStatusManager {
	private static EmailStatusManager instance = new EmailStatusManager();

	private EmailStatusListener listener;

	private EmailStatusManager() {
	}

	public static EmailStatusManager instance() {
		return instance;
	}

	public void setListener(EmailStatusListener listener) {
		this.listener = listener;
	}

	public void notifyListener(String message) {
		if (isListenerDefined()) {
			listener.statusChanged(message);
		}
	}

	public void notifyMessageDownloadProgress(int value) {
		if (isListenerDefined()) {
			listener.notifyMessageDownloadProgress(value);
		}
	}

	private boolean isListenerDefined() {
		return listener != null;
	}

}
