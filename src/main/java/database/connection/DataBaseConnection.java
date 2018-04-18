package database.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface DataBaseConnection {
	public Connection getConnection() throws SQLException;
}
