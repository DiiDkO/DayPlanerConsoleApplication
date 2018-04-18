package database.selector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import event.Event;

public class EventSelector {
	Connection conn;
	private LocalDate localDate;
	private Date date;

	public EventSelector(Connection conn) throws ClassNotFoundException, SQLException {
		this.conn = conn;
		Statement stm = conn.createStatement();
		stm.execute("USE dayplannerevents;");
		date = new Date();
		localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public List<Event> selectDataFromDateToDate(LocalDate fromDate, LocalDate toDate, String username)
			throws SQLException {
		List<Event> list = new ArrayList<Event>();
		String sqlStm = "SELECT events.name, events.startTime, events.endTime, events.MOTD ,events.description FROM events"
				+ " WHERE events.user_id= ?  AND DATE (startTime)>= ?  AND DATE(startTime)<= ? ORDER BY startTime ASC;";
		PreparedStatement preStm = conn.prepareStatement(sqlStm);
		preStm.setInt(1, this.getUserIdFromDataBase(username));
		preStm.setString(2, fromDate.toString());
		preStm.setString(3, toDate.toString());
		ResultSet res = preStm.executeQuery();
		while (res.next()) {
			Event event = new Event();
			event.setName(res.getString(1));
			event.setStartTime(res.getTimestamp(2));
			event.setEndTime(res.getTimestamp(3));
			event.setMOTD(res.getBoolean(4));
			event.setDescription(res.getString(5));
			list.add(event);
		}
		res.close();
		preStm.close();
		return list;
	}

	public List<Event> selectDataForDayFromCurrecntMonth(int day, String username) throws SQLException {
		List<Event> list = new ArrayList<Event>();
		String sqlStm = "SELECT events.name, events.startTime, events.endTime, events.MOTD, events.description FROM events  WHERE  events.user_id= ? "
				+ " AND DAY (startTime)= ?  AND MONTH(startTime)= ? AND  YEAR(startTime)= ? ORDER BY startTime ASC ;";
		PreparedStatement preStm = conn.prepareStatement(sqlStm);
		preStm.setInt(1, this.getUserIdFromDataBase(username));
		preStm.setInt(2, day);
		preStm.setInt(3, this.localDate.getMonthValue());
		preStm.setInt(4, this.localDate.getYear());
		ResultSet res = preStm.executeQuery();
		while (res.next()) {

			Event event = new Event();
			event.setName(res.getString(1));
			event.setStartTime(res.getTimestamp(2));
			event.setEndTime(res.getTimestamp(3));
			event.setMOTD(res.getBoolean(4));
			event.setDescription(res.getString(5));
			list.add(event);
		}
		res.close();
		preStm.close();
		return list;
	}

	public List<Event> selectDataForDayFromMonth(int day, int month, String username) throws SQLException {
		List<Event> list = new ArrayList<Event>();
		String sqlStm = "SELECT events.name, events.startTime, events.endTime, events.MOTD,events.description FROM events  WHERE  events.user_id= ? "
				+ " AND DAY (startTime)= ?  AND MONTH(startTime)= ? AND  YEAR(startTime)= ? ORDER BY startTime ASC;";
		PreparedStatement preStm = conn.prepareStatement(sqlStm);
		preStm.setInt(1, this.getUserIdFromDataBase(username));
		preStm.setInt(2, day);
		preStm.setInt(3, month);
		preStm.setInt(4, this.localDate.getYear());
		ResultSet res = preStm.executeQuery();
		while (res.next()) {

			Event event = new Event();
			event.setName(res.getString(1));
			event.setStartTime(res.getTimestamp(2));
			event.setEndTime(res.getTimestamp(3));
			event.setMOTD(res.getBoolean(4));
			event.setDescription(res.getString(5));
			list.add(event);
		}
		res.close();
		preStm.close();
		return list;
	}

	public List<Event> selectDataFortWeekOfMonth(int month, int year, int week, String username) throws SQLException {
		List<Event> list = new ArrayList<Event>();
		String sqlStm = weekOfMonth(week);
		PreparedStatement preStm = conn.prepareStatement(sqlStm);
		preStm.setInt(1, this.getUserIdFromDataBase(username));
		preStm.setInt(2, month);
		preStm.setInt(3, year);
		ResultSet res = preStm.executeQuery();
		while (res.next()) {
			Event event = new Event();
			event.setName(res.getString(1));
			event.setStartTime(res.getTimestamp(2));
			event.setEndTime(res.getTimestamp(3));
			event.setMOTD(res.getBoolean(4));
			event.setDescription(res.getString(5));
			list.add(event);
		}
		res.close();
		preStm.close();
		return list;
	}

	public List<Event> selectDataFortWeekOfCurrentMonth(int week, String username) throws SQLException {
		List<Event> list = new ArrayList<Event>();
		String sqlStm = weekOfMonth(week);
		PreparedStatement preStm = conn.prepareStatement(sqlStm);
		preStm.setInt(1, this.getUserIdFromDataBase(username));
		preStm.setInt(2, this.localDate.getMonthValue());
		preStm.setInt(3, this.localDate.getYear());
		ResultSet res = preStm.executeQuery();
		while (res.next()) {
			Event event = new Event();
			event.setName(res.getString(1));
			event.setStartTime(res.getTimestamp(2));
			event.setEndTime(res.getTimestamp(3));
			event.setMOTD(res.getBoolean(4));
			event.setDescription(res.getString(5));
			list.add(event);
		}
		res.close();
		preStm.close();
		return list;
	}

	private String weekOfMonth(int week) throws SQLException {
		String sqlStm = null;
		switch (EventSelectorAction.parseInt(week)) {
		case FIRSTWEEK:
			sqlStm = "SELECT events.name,events.startTime,events.endTime, events.MOTD, events.description FROM events"
					+ " WHERE events.user_id= ?" + " AND MONTH (startTime)= ? "
					+ " AND  YEAR(startTime)= ? AND DAY(startTime) >=1 AND DAY(startTime)<=7 ORDER BY startTime ASC ;";
			break;
		case SECONDWEEK:
			sqlStm = "SELECT events.name, events.startTime,events.endTime, events.MOTD ,events.description FROM events WHERE events.user_id=? "
					+ " AND MONTH (startTime)=? AND  YEAR(startTime)= ? AND DAY(startTime) >=8 AND DAY(startTime)<=14 ORDER BY startTime ASC;";
			break;

		case THIRDWEEK:
			sqlStm = "SELECT events.name, events.startTime,events.endTime, events.MOTD,events.description FROM events WHERE events.user_id=? "
					+ " AND MONTH (startTime)= ? AND  YEAR(startTime)= ? AND DAY(startTime) >=15 AND DAY(startTime)<=21 ORDER BY startTime ASC;";
			break;
		case FORTHWEEK:
			sqlStm = "SELECT events.name, events.startTime, events.endTime, events.MOTD,events.description FROM events WHERE events.user_id= ? "
					+ " AND MONTH (startTime)= ? AND  YEAR(startTime)= ? AND DAY(startTime) >=22  AND  DAY(startTime) <=28  ORDER BY startTime ASC;";
			break;

		case FIFTHWEEK:
			sqlStm = "SELECT events.name, events.startTime, events.endTime, events.MOTD ,events.description FROM events WHERE events.user_id= ? "
					+ " AND MONTH (startTime)= ?  AND  YEAR(startTime)= ?  AND DAY(startTime)>=29 AND DAY(startTime)<=31 ORDER BY startTime ASC;";
			break;

		default:
			System.out.println("Ivalid week of a month!");
			break;
		}
		return sqlStm;
	}

	public List<Event> selectAllDataFromCurrentMonth(String username) throws SQLException {
		List<Event> list = new ArrayList<Event>();
		String sqlStm = "SELECT name, startTime,endTime, MOTD, description FROM events"
				+ " WHERE events.user_id= ? AND MONTH (startTime)= ? ORDER BY startTime ASC ;";
		PreparedStatement preStm = conn.prepareStatement(sqlStm);
		preStm.setInt(1, this.getUserIdFromDataBase(username));
		preStm.setInt(2, this.localDate.getMonthValue());
		ResultSet res = preStm.executeQuery();
		while (res.next()) {
			Event event = new Event();
			event.setName(res.getString(1));
			event.setStartTime(res.getTimestamp(2));
			event.setEndTime(res.getTimestamp(3));
			event.setMOTD(res.getBoolean(4));
			event.setDescription(res.getString(5));
			list.add(event);
		}
		res.close();
		preStm.close();
		return list;
	}

	public List<Event> selectAllDataFromMonth(int month, int year, String username) throws SQLException {
		List<Event> list = new ArrayList<Event>();
		String sqlStm = "SELECT name, startTime,endTime, MOTD, description FROM events"
				+ " WHERE events.user_id= ? AND MONTH (startTime)= ? AND YEAR(startTime)= ? ORDER BY startTime ASC;";
		PreparedStatement preStm = conn.prepareStatement(sqlStm);
		preStm.setInt(1, this.getUserIdFromDataBase(username));
		preStm.setInt(2, month);
		preStm.setInt(3, year);
		ResultSet res = preStm.executeQuery();
		while (res.next()) {
			Event event = new Event();
			event.setName(res.getString(1));
			event.setStartTime(res.getTimestamp(2));
			event.setEndTime(res.getTimestamp(3));
			event.setMOTD(res.getBoolean(4));
			event.setDescription(res.getString(5));
			list.add(event);
		}
		res.close();
		preStm.close();
		return list;
	}

	public Event getMOTD(Timestamp dateTime, String username) throws SQLException {
		Event event = new Event();
		String sqlStm = "SELECT name, startTime, endTime, description from events"
				+ " WHERE events.user_id= ? AND startTime= ? AND endTime= ? AND MOTD= ? ORDER BY startTime ASC ;";
		PreparedStatement preStm = conn.prepareStatement(sqlStm);
		preStm.setInt(1, this.getUserIdFromDataBase(username));
		preStm.setTimestamp(2, dateTime);
		preStm.setTimestamp(3, dateTime);
		preStm.setBoolean(4, true);
		ResultSet res = preStm.executeQuery();
		if (!res.next()) {
			return null;
		}
		event.setName(res.getString(1));
		event.setStartTime(res.getTimestamp(2));
		event.setEndTime(res.getTimestamp(3));
		event.setDescription(res.getString(4));
		return event;
	}

	private int getUserIdFromDataBase(String username) throws SQLException {
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
