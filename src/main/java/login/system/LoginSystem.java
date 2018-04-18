package login.system;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import user.User;

public class LoginSystem {
	private Connection conn = null;
	private final String sqlStm = "SELECT username, password FROM dayplannerevents.users ;";

	public LoginSystem(Connection conn) throws ClassNotFoundException, SQLException {
		this.conn = conn;
	}

	public boolean Login(User user) throws SQLException {
		if (loginCheck(user)) {
			return true;
		}
		return false;
	}

	private boolean loginCheck(User user) throws SQLException {
		try (Statement stm = this.conn.createStatement(); ResultSet res = stm.executeQuery(sqlStm)) {

			while (res.next()) {
				String username = res.getString(1);
				if (username.equals(user.getUsername()) && (res.getString(2)).equals(user.getPassword()))
					return true;
			}
			return false;
		}
	}
}
