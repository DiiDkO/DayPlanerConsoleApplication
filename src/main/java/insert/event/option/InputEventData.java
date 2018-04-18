package insert.event.option;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Scanner;

import event.Event;

public class InputEventData {
	static Scanner in = new Scanner(System.in);
	static Scanner integer = new Scanner(System.in);

	public Event getEvent() {
		return inputEvent();
	}

	public Event inputEvent() {
		System.out.print("Event name: ");
		String eventName = in.nextLine();
		Timestamp startDTime = inputStartDateTime();
		LocalDate date = startDTime.toLocalDateTime().toLocalDate();
		Timestamp endDTime = inputEndTime(date, startDTime.toLocalDateTime().toLocalTime().getHour());
		System.out.print("Event description: ");
		String description = in.nextLine();
		return new Event(eventName, startDTime, endDTime, false, description);
	}

	public static boolean isBeforeStartHour(int startHour, int endHour) {
		if (endHour < startHour)
			return true;
		return false;
	}

	public static int pickEndHour(int startHour, int endHour) {
		while (endHour < startHour) {
			System.out.println("Input valid endHour!");
			endHour = inputStartEndHour(false);
		}
		return endHour;
	}

	private static int inputStartEndHour(boolean startHour) {
		System.out.print((startHour ? "Start " : "End ") + " hour: ");
		int hour = integer.nextInt();
		while (hour > 23) {
			System.out.println("Invalid hour!");
			System.out.print((startHour ? "Start " : "End ") + " hour: ");
			hour = integer.nextInt();
		}
		return hour;
	}

	public static int inputYear() {
		System.out.print("Year: ");
		int year = integer.nextInt();
		return year;
	}

	public static int inputMonth() {
		System.out.print("Month: ");
		int month = integer.nextInt();
		while (month < 1 || month > 12) {
			System.out.println("Invalid month!");
			System.out.print("Month: ");
			month = integer.nextInt();
		}
		return month;
	}

	public static int inputDay(int month, int year) {
		LocalDate date = LocalDate.of(year, month, 1);
		System.out.print("Day: ");
		int day = integer.nextInt();
		while (day < 1 || day > date.lengthOfMonth()) {
			System.out.println("Invalid day of month!");
			System.out.print("Day:");
			day = integer.nextInt();
		}

		return day;
	}

	public static Timestamp inputStartDateTime() {
		System.out.println("Event start dateTime:");
		int year = inputYear();
		int month = inputMonth();
		int day = inputDay(month, year);
		String startDTime = null;
		int startHour = inputStartEndHour(true);
		startDTime = year + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day) + " "
				+ (startHour < 10 ? "0" + startHour : startHour) + ":00:00";
		return Timestamp.valueOf(startDTime);
	}

	public static Timestamp inputEndTime(LocalDate startDTime, int startHour) {
		System.out.println("Event end time");
		String endDTime = null;
		int year = startDTime.getYear();
		int month = startDTime.getMonthValue();
		int day = startDTime.getDayOfMonth();
		int endHour = inputStartEndHour(false);
		if (isBeforeStartHour(startHour, endHour))
			endHour = pickEndHour(startHour, endHour);
		endDTime = (year + "-" + ((month < 10) ? ("0" + month) : month) + "-" + (day < 10 ? ("0" + day) : day) + " "
				+ (endHour < 10 ? "0" + endHour : endHour) + ":00:00");
		return Timestamp.valueOf(endDTime);
	}
	public static Event inputMOTD() {
		int year=InputEventData.inputYear();
		int month=InputEventData.inputMonth();
		int day=InputEventData.inputDay(month, year);
		Timestamp dateTime=Timestamp.valueOf(year+"-"+(month<10?"0"+month:month)+"-"+(day<10?"0"+day:day)+" 00:00:00");
		String description= inputMOTDDescription();
		return new Event("MOTD",dateTime,dateTime,true,description);
	}
	public Event getEventNameStarEndDateTime() {
		System.out.print("Event name:");
		String eventName = in.nextLine();
		Timestamp startDTime = InputEventData.inputStartDateTime();
		LocalDate date = startDTime.toLocalDateTime().toLocalDate();
		System.out.println(date.toString());
		int startHour = startDTime.toLocalDateTime().toLocalTime().getHour();
		Timestamp endDTime = InputEventData.inputEndTime(date, startHour);
		return new Event(eventName, startDTime, endDTime);
	}

	public static String description() {
		System.out.print("Event description: ");
		return in.nextLine();
	}
	public static String inputMOTDDescription() {
		System.out.print("MOTD : ");
		return in.nextLine();
	}
}
