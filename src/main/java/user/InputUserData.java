package user;

import java.util.Scanner;

public class InputUserData {
	static Scanner in =new Scanner(System.in);
	public static User inputUser() {
		System.out.println("Username: ");
		String uname = in.nextLine();
		System.out.print("Password:");
		String password = in.nextLine();
		return new User(uname, password);
	}
	public static boolean checkInputUser(User user) {
		User inputUser = InputUserData.inputUser();
		if (user.getUsername().equals(inputUser.getUsername()) && user.getPassword().equals(inputUser.getPassword()))
			return true;
		return false;
	}
}
