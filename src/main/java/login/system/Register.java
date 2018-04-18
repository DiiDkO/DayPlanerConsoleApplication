package login.system;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.manager.DatabaseManager;
import user.PasswordException;
import user.User;
import user.UsernameException;
import user.password.validation.PasswordValidator;
import user.password.validation.UsernameValidator;
import user.password.validation.Validator;

public class Register {
	private User user;
	private Connection conn;

	public User getUser() {
		return user;
	}

	public Register(User user) throws UsernameException, PasswordException {

		this.user = user;
	}

	public boolean checkUserValidation() throws UsernameException, PasswordException {
			if(this.checkUsernameValidation() && this.checkPasswordValidation())
				return true;
			return false;
		}

	private boolean checkPasswordValidation() throws UsernameException, PasswordException {
		boolean valid = false;
		Validator<String> validatePassword = new PasswordValidator();
		if (!validatePassword.validate(this.user.getPassword()))
			throw new PasswordException();
		else
			valid = true;
		return valid;
	}

	private boolean checkUsernameValidation() throws UsernameException {
		boolean valid = false;
		Validator<String> validateUsername = new UsernameValidator();
		if (!validateUsername.validate(this.user.getUsername()))
			throw new UsernameException();
		else
			valid = true;
		return valid;
	}

	public boolean userExist(Connection conn) throws ClassNotFoundException, SQLException {
		this.conn = conn;
		DatabaseManager insert = new DatabaseManager(this.conn);
		try (Statement stm = insert.getStm();
				ResultSet res = stm.executeQuery("SELECT username FROM dayplannerevents.users ;")) {
			while (res.next())
				if (res.getString(1).equals(user.getUsername()))
					return true;
			return false;
		}
	}
}
