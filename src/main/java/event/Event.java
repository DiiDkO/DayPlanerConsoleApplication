package event;

import java.sql.Timestamp;

public class Event {
	private int id;
	private String name;
	private Timestamp startTime;
	private Timestamp endTime;
	private String description;
	private boolean MOTD;
	private int user_id;

	public Event(String name, Timestamp startTime, Timestamp endTime,boolean MOTD, String description) {
		this.setName(name);
		this.setStartTime(startTime);
		this.setEndTime(endTime);
		this.setMOTD(MOTD);
		this.setDescription(description);
	}

	public Event(String name, Timestamp startTime, Timestamp endTime) {
		this.setName(name);
		this.setStartTime(startTime);
		this.setEndTime(endTime);
	}

	public Event(String name, String description) {

		this.setName(name);
		this.setDescription(description);
	}

	public Event() {

		this.name = null;
		this.startTime = null;
		this.endTime = null;
		this.MOTD = false;
		this.description = null;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public boolean isMOTD() {
		return MOTD;
	}

	public void setMOTD(boolean MOTD) {
		this.MOTD = MOTD;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
}
