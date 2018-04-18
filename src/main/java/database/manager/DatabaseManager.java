package database.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import event.Event;
import user.User;

public class DatabaseManager {
	private Connection conn;
	private Statement stm;

	public DatabaseManager(Connection conn) throws ClassNotFoundException, SQLException {
		this.conn = conn;
		stm = conn.createStatement();
		stm.execute("USE dayplannerevents;");
	}

	public Statement getStm() {
		return stm;
	}

	public boolean insertUser(User user) throws SQLException {
		String sqlStm = "INSERT INTO users(username,password) VALUES(?,?);";
		PreparedStatement preStm = conn.prepareStatement(sqlStm);
		preStm.setString(1, user.getUsername());
		preStm.setString(2, user.getPassword());
		if (preStm.executeUpdate() == 1) {
			preStm.close();
			return true;
		} else {
			preStm.close();
			return false;
		}
	}

	public boolean deleteUser(String username) throws SQLException {
		String sqlStm = "DELETE FROM users WHERE users.id= ? ;";
		PreparedStatement preStm = conn.prepareStatement(sqlStm);
		preStm.setInt(1, this.getUserIdFromDataBase(username));
		if (preStm.executeUpdate() == 1) {
			preStm.close();
			return true;
		} else {
			preStm.close();
			return false;
		}
	}

	public boolean updateUserPassword(String password, User user) throws SQLException {
		String sqlStm = "UPDATE users SET password = '" + password + "' WHERE users.id = ? ;";
		PreparedStatement preStm = conn.prepareStatement(sqlStm);
		preStm.setInt(1, this.getUserIdFromDataBase(user.getUsername()));
		if (preStm.executeUpdate() == 1) {
			preStm.close();
			return true;
		} else {
			preStm.close();
			return false;
		}
	}

	public boolean insertEvent(Event event, String username) throws SQLException {
		String sqlStm = "INSERT INTO events(name,startTime,endTime,MOTD,description,user_id) VALUES(?, ?, ?, ?, ?, ?);";
		PreparedStatement preStm = conn.prepareStatement(sqlStm, Statement.RETURN_GENERATED_KEYS);
		preStm.setString(1, event.getName());
		preStm.setTimestamp(2, event.getStartTime());
		preStm.setTimestamp(3, event.getEndTime());
		preStm.setBoolean(4, event.isMOTD());
		preStm.setString(5, event.getDescription());
		preStm.setInt(6, this.getUserIdFromDataBase(username));
		if (preStm.executeUpdate() == 1) {
			ResultSet generateKeys = preStm.getGeneratedKeys();
			while (generateKeys.next()) {
				event.setId(generateKeys.getInt(1));
			}
			event.setUser_id(this.getUserIdFromDataBase(username));
			preStm.close();
			return true;
		} else {
			preStm.close();
			return false;
		}

	}

	public boolean deleteAllEvent(String username) throws SQLException {
		String sqlStm = "DELETE FROM events WHERE events.user_id = ? ;";
		PreparedStatement preStm = conn.prepareStatement(sqlStm);
		preStm.setInt(1, this.getUserIdFromDataBase(username));
		if (preStm.executeUpdate() == 1) {
			preStm.close();
			return true;
		} else {
			preStm.close();
			return false;
		}
	}

	public boolean deleteEvent(Event event, String username) throws SQLException {
		String sqlStm = "DELETE FROM events WHERE events.id= ?  AND events.user_id = ? ;";
		PreparedStatement preStm = conn.prepareStatement(sqlStm);
		preStm.setInt(1, this.getEventIdFromDataBase(event));
		preStm.setInt(2, this.getUserIdFromDataBase(username));
		if (preStm.executeUpdate() == 1) {
			preStm.close();
			return true;
		} else {
			preStm.close();
			return false;
		}
	}

	public boolean deleteOnDateTime(Timestamp eventStartTime, Timestamp eventEndTime, String username)
			throws SQLException {
		String sqlStm = "DELETE FROM events WHERE events.startTime= ? AND events.endTime= ?  AND events.user_id = ? ;";
		PreparedStatement preStm = conn.prepareStatement(sqlStm);
		preStm.setTimestamp(1, eventStartTime);
		preStm.setTimestamp(2, eventEndTime);
		preStm.setInt(3, this.getUserIdFromDataBase(username));
		if (preStm.executeUpdate() == 1) {
			preStm.close();
			return true;
		} else {
			preStm.close();
			return false;
		}
	}

	public boolean updateEventName(String eventName, Event event, String username) throws SQLException {
		String sqlStm = " UPDATE events SET name= ? WHERE events.id= ? AND events.user_id = ? ; ";
		PreparedStatement preStm = conn.prepareStatement(sqlStm);
		preStm.setString(1, eventName);
		preStm.setInt(2, this.getEventIdFromDataBase(event));
		preStm.setInt(3, this.getUserIdFromDataBase(username));
		if (preStm.executeUpdate() == 1) {
			preStm.close();
			return true;
		} else {
			preStm.close();
			return false;
		}
	}

	public boolean updateEventStartTime(Timestamp startTime, Event event, String username) throws SQLException {
		String sqlStm = " UPDATE events SET startTime= ? " + " WHERE events.id= ? AND events.user_id = ? ; ";
		PreparedStatement preStm = conn.prepareStatement(sqlStm);
		preStm.setTimestamp(1, startTime);
		preStm.setInt(2, this.getEventIdFromDataBase(event));
		preStm.setInt(3, this.getUserIdFromDataBase(username));
		if (preStm.executeUpdate() == 1) {
			preStm.close();
			return true;
		} else {
			preStm.close();
			return false;
		}
	}

	public boolean updateEventEndTime(Timestamp endTime, Event event, String username) throws SQLException {
		String sqlStm = " UPDATE events SET endTime= ? " + " WHERE events.id= ? AND events.user_id = ? ; ";
		PreparedStatement preStm = conn.prepareStatement(sqlStm);
		preStm.setTimestamp(1, endTime);
		preStm.setInt(2, this.getEventIdFromDataBase(event));
		preStm.setInt(3, this.getUserIdFromDataBase(username));
		if (preStm.executeUpdate() == 1) {
			preStm.close();
			return true;
		} else {
			preStm.close();
			return false;
		}
	}

	public boolean updateEventDescription(String descrip, Event event, String username) throws SQLException {
		String sqlStm = " UPDATE events SET description='" + descrip + "'"
				+ " WHERE events.id= ? AND events.user_id = ? ;";
		PreparedStatement preStm = conn.prepareStatement(sqlStm);
		preStm.setInt(1, this.getEventIdFromDataBase(event));
		preStm.setInt(2, this.getUserIdFromDataBase(username));
		if (preStm.executeUpdate() == 1) {
			preStm.close();
			return true;
		} else {
			preStm.close();
			return false;
		}
	}

	private int getEventIdFromDataBase(Event event) throws SQLException {
		String selectEventId = "SELECT id FROM events WHERE events.name= ?  AND events.startTime= ? AND events.endTime= ? ;";
		int event_id = 0;
		PreparedStatement preStm = conn.prepareStatement(selectEventId);
		preStm.setString(1, event.getName());
		preStm.setTimestamp(2, event.getStartTime());
		preStm.setTimestamp(3, event.getEndTime());
		ResultSet res = preStm.executeQuery();
		while (res.next()) {
			event_id = res.getInt(1);
		}
		res.close();
		return event_id;
	}
	private  int getUserIdFromDataBase(String username) throws SQLException {
		String selectUserId = "SELECT id FROM users WHERE users.username= ?  ;";
		int user_id = 0;
		PreparedStatement preStm = conn.prepareStatement(selectUserId);
		preStm.setString(1, username);
		ResultSet res = preStm.executeQuery();
		while (res.next()) {
			user_id = res.getInt(1);
		}
		res.close();
		preStm.close();
		return user_id;
	}
}