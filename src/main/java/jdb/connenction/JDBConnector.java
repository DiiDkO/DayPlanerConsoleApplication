package jdb.connenction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import database.connection.DataBaseConnection;

public class JDBConnector implements DataBaseConnection {
	static final String JDBC_URL = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/";
	static final String user = "root";
	static final String pass = "ДииДк00972202";

	public JDBConnector() throws ClassNotFoundException {
		System.out.println("Connecting ...");
		Class.forName(JDBC_URL);
	}

	public Connection getConnection() throws SQLException {

		return DriverManager.getConnection(DB_URL, user, pass);

	}

}
