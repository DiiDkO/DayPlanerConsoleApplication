package user;

@SuppressWarnings("serial")
public class UsernameException extends Exception {
	private String message;

	public UsernameException(String message) {
		this.message = message;
	}

	public UsernameException() {
		this.message = "Username must contain upper and lower letters and numbers and must be 6 signs at least\n";
	}

	public String getMessage() {
		return this.message;
	}

}
