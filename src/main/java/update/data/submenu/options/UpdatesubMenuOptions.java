package update.data.submenu.options;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Scanner;

import insert.event.option.InputEventData;
import user.InputUserData;
import user.User;

public class UpdatesubMenuOptions {
	Scanner in = new Scanner(System.in);

	public UpdatesubMenuOptions() {

	}

	public String updateUserPassword(User user) {

		String newPass = null;
		if (InputUserData.checkInputUser(user)) {
			System.out.println("New password: ");
			newPass = in.nextLine();
			System.out.println("The password is changed!");
		} else {
			System.out.println("Invalid username/password!");
			while (true) {
				System.out.println("Input valid data:");
				if (InputUserData.checkInputUser(user)) {
					System.out.println("New password: ");
					newPass = in.nextLine();
					System.out.println("The password is changed!");
					break;
				}
				continue;
			}
		}
		return newPass;
	}

	public String updateEventDescription() {
		System.out.print("Update event description: ");
		return in.nextLine();
	}
	public String updateMOTD() {
		System.out.print("Update MOTD: ");
		return in.nextLine();
	}
	public Timestamp updateStartTime() {
		System.out.println("Update event's start date time:");
		return InputEventData.inputStartDateTime();
	}

	public Timestamp updateEndTime(LocalDate startDTime, int startHour) {
		System.out.println("Update event's end time:");
		return InputEventData.inputEndTime(startDTime, startHour);
	}

	public String updateEventName() {
		System.out.print("New event name: ");
		return in.nextLine();
	}	
}
