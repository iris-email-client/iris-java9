package br.unb.cic.iris.gui.command.list;

import java.text.SimpleDateFormat;
import java.util.Date;

//TODO mover para um modulo 'base' para ser usada antes de salvar o email
public class ListMessagesUtil {
	private static SimpleDateFormat simpleFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private static SimpleDateFormat fullFormatter = new SimpleDateFormat("EEE, dd/MMM/yyyy HH:mm:ss");
	
	public static String parseEmail(String email){
		//parse emails in the form: UTF-8 <xxx@aaa.com>,
		//returning just the email
		String result = "";
		String split[] = email.split("<");
		if(split.length > 1){
			result = split[1].split(">")[0];
		} else {
			result = split[0];
		}
		return result.trim();
	}
	
	public static String parseDateSimple(Date date){		
		return simpleFormatter.format(date);
	}
	public static String parseDateFull(Date date){		
		return fullFormatter.format(date);
	}
	
}
