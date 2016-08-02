package br.unb.cic.iris.mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/*** added by dSecurityBaseMail
 */
public class MessageUtils {
	public static final String MIME_TEXT = "text/plain";
	public static final String MIME_HTML = "text/html";
	
	public static final String MIME_MULTIPART_SIGNED = "multipart/signed";
	public static final String MIME_MULTIPART_ENCRYPTED = "multipart/encrypted";
	
	public static final String MIME_SMIME = "application/pkcs7-mime";
	public static final String MIME_SMIME_2 = "application/x-pkcs7-mime";
	
	
	public static String getMimeType(String contentType) {
		String str = "unknown";
		StringTokenizer st = new StringTokenizer(contentType.trim(), ";");
		if(st.hasMoreTokens()) {
			str = st.nextToken();
		}
		return str.toLowerCase();
	}
	public static String getStringFromInputStream(InputStream is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			if(br != null) {
				try {
					br.close();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
}