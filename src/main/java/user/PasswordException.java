package user;

@SuppressWarnings("serial")
public class PasswordException extends Exception {
	private String message;

	public PasswordException(String message) {
		this.message = message;
	}

	public PasswordException() {
		this.message="The password must contain upper and lower letters and numbers and must be 6 signs at least\n";
	}

	public String getMessage() {
		return message;
	}

}
