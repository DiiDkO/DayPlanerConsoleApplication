package user.password.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameValidator implements Validator<String> {

	public boolean validate(String data) {
		Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$");
		Matcher m = p.matcher(data);
		return m.matches();
	}

}
