package select.submenu.options;

import java.time.LocalDate;
import java.util.Scanner;

import insert.event.option.InputEventData;

public class SelectSubmenuOptions {
	private Scanner in;

	public SelectSubmenuOptions() {
		in = new Scanner(System.in);
	}

	public int inputMonth() {
		return InputEventData.inputMonth();
	}

	public int inputDay(int month, int year) {
		return InputEventData.inputDay(month, year);
	}

	public int inputYear() {
		return InputEventData.inputYear();
	}

	public int display_numberOfWeeks(int month, int year) {
		int days = LocalDate.of(year, month, 1).getMonth().maxLength();
		if (days > 28) {
			System.out.println("\nThe month has 5 weeks!");
			return 5;
		} else {
			System.out.println("The month has 4 weeks!");
			return 4;
		}
	}

	public boolean numberOfWeek(int number) {
		if (number == 5)
			return true;
		return false;
	}

	public int selectWeekOfMonth() {
		return in.nextInt();
	}

	public LocalDate inputFrom_ToData(boolean data) {
		System.out.println(data ? "From data: " : "To data: ");
		int year = this.inputYear();
		int month = this.inputMonth();
		int day = this.inputDay(month, year);
		LocalDate date = LocalDate.of(year, month, day);
		return date;
	}
}
