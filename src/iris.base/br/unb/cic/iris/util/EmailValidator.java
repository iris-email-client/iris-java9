package br.unb.cic.iris.util;

import java.util.regex.Pattern;

/***
 * added by dBase
 */
public class EmailValidator {
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static Pattern pattern = Pattern.compile(EMAIL_PATTERN);

	public static boolean validate(final String email) {
		return pattern.matcher(email).matches();
	}
}