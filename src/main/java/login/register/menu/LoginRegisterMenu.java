package login.register.menu;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import database.manager.DatabaseManager;
import jdb.connenction.EventsDataBaseConnection;
import jdb.connenction.JDBConnector;
import login.system.LoginSystem;
import login.system.Register;
import user.PasswordException;
import user.User;
import user.UsernameException;

public class LoginRegisterMenu {
	Scanner in = new Scanner(System.in);
	Scanner in1 = new Scanner(System.in);
	private User user;
	private UserAction option;

	public LoginRegisterMenu() throws ClassNotFoundException, SQLException, UsernameException, PasswordException {
		User user = null;
		try (Connection conn = new JDBConnector().getConnection()) {

			EventsDataBaseConnection dbConn = new EventsDataBaseConnection();
			if (!dbConn.checkDatabaseExist(conn))
				System.out.println("Database have already existed !\n");
			this.displayLoginRegOptions();
			option = this.inputMenuOption();
			user = this.menuOptions(option);
		}
		try (Connection conn1 = new JDBConnector().getConnection()) {
			LoginSystem login = new LoginSystem(conn1);
			if (!loginCheck(user, login))
			user=this.retryLoginOrRegistrationOption();
		}
		this.user = user;
	}

	private User retryLoginOrRegistrationOption()
			throws ClassNotFoundException, SQLException, UsernameException, PasswordException {
		User user=null;
		try (Connection connLogReg = new JDBConnector().getConnection()) {
			System.out.println("Do you want to try logging in again or register? ");
			System.out.println("Press 1 for log in\nPress 2 for register");
			System.out.print("Choice: ");
			UserAction option = this.inputMenuOption();
			user = this.menuOptions(option);
			LoginSystem login = new LoginSystem(connLogReg);
			if (!loginCheck(user, login))
				user=this.retryLoginOrRegistrationOption();
		}
		return user;
	}

	public User getUser() {
		return this.user;
	}

	private Register registrateUser() throws UsernameException, PasswordException {
		Register regUser = new Register(this.inputDataUser());
		return regUser;
	}

	private User menuOptions(UserAction option)
			throws ClassNotFoundException, SQLException, UsernameException, PasswordException {
		User user = new User();

		switch (option) {
		case LOGIN:
			return loginExecution();
		case REGISTRATION:
			return registrationExecution();
		}
		return null;
	}

	private UserAction pickMenuOption() {
		UserAction option = UserAction.UNKNOWN;
		option = UserAction.parseInt(Integer.parseInt(in.nextLine()));
		while (option.equals(UserAction.UNKNOWN)) {
			System.out.println("\nInvalid input!\nInput valid option!");
			System.out.print("Select: ");
			option = UserAction.parseInt(Integer.parseInt(in.nextLine()));
		}
		return option;

	}

	private UserAction inputMenuOption() {
		try {
			return this.pickMenuOption();
		} catch (NumberFormatException e) {
			System.out.println("\nInvalid input!\nInput valid option!");
			System.out.print("Select: ");
			return this.inputMenuOption();
		}

	}

	private User loginExecution() {
		User user = new User();
		this.display_loginReg("Log in:");
		user = this.inputDataUser();
		return user;
	}

	private User registrationExecution() throws SQLException, ClassNotFoundException {
		Register regUser = null;
		this.display_loginReg("Registration:");
		try (Connection connReg = new JDBConnector().getConnection()) {
			try {
				regUser = registrateUser();

				if (regUser.checkUserValidation()) {
					final boolean reg = regUser.userExist(connReg);
					if (!reg) {
						final DatabaseManager insertUser = new DatabaseManager(connReg);
						insertUser.insertUser(regUser.getUser());
						System.out.println("Registration done!");
					} else {
						System.out.println("User exist!");
						this.registrationExecution();
					}
				}
			} catch (UsernameException | PasswordException e) {
				System.out.println(e.getMessage());
				this.registrationExecution();
			}
		}
		return regUser.getUser();
	}

	private boolean loginCheck(User user, LoginSystem login) throws SQLException {
		if (login.Login(user)) {
			System.err.println("\nWelcome!\n");
			return true;
		} else {
			System.out.println("Invalid username/password!\n");
			return false;
		}
	}

	private void display_loginReg(String logReg) {
		System.out.println("|------------------------------------|");
		System.out.format("|%20s %17s", logReg, "|\n");
		System.out.println("|------------------------------------|\n");

	}

	private void displayLoginRegOptions() {
		System.out.println("Selection:");
		System.out.println("1)Log in\n2)Registration\n");
		System.out.print("Select: ");
	}

	private User inputDataUser() {
		System.out.print("Username: ");
		String username = in.nextLine();
		System.out.print("Password: ");
		String password = in.nextLine();
		return new User(username, password);
	}
}
