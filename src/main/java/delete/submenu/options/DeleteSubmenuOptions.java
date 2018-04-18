package delete.submenu.options;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Scanner;

import event.Event;
import insert.event.option.InputEventData;
import user.InputUserData;
import user.User;

public class DeleteSubmenuOptions {
	Scanner in = new Scanner(System.in);

	public DeleteSubmenuOptions() {
	}

	public Event getEvent() {
		return new InputEventData().getEventNameStarEndDateTime();
	}

	public Timestamp deleteStartDateTime() {
		return InputEventData.inputStartDateTime();
	}

	public Timestamp deleteEndDateTime(LocalDate startDTime, int startHour) {
		return InputEventData.inputEndTime(startDTime, startHour);
	}
	public Event deleteMOTD() {
		return InputEventData.inputMOTD();
	}

	public User comfirmUser(User user) {
		if (InputUserData.checkInputUser(user))
			return user;
		else {
			System.out.println("Invalid username/password!");
			while (true) {
				System.out.println("Input valid data:");
				if (InputUserData.checkInputUser(user)) {
					System.out.println("User confirmed!");
					return user;
				}
				continue;
			}
		}
	}
}
