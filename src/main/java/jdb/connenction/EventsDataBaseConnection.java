package jdb.connenction;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class EventsDataBaseConnection {
	final String databaseName = "dayplannerevents";

	public EventsDataBaseConnection() {
	}

	public boolean checkDatabaseExist(Connection con) throws SQLException {
		if (this.createDataBase(con) == 1) {
			System.out.println("Database not exist.\nCreating database ...\n");
			return false;
		} else
			return true;
	}

	private int createDataBase(Connection con) throws SQLException {
		Statement stm = con.createStatement();
		int res = stm.executeUpdate("CREATE DATABASE IF NOT EXISTS " + databaseName + " ;");
		stm.close();
		return res;
	}

}
