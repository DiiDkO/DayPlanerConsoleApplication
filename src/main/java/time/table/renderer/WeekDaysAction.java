package time.table.renderer;

public enum WeekDaysAction {
	FIRSTWEEK(1), SECONDWEEK(2), THIRDWEEK(3), FORTHWEEK(4), FIFTWEEK(5);
	private int id;

	WeekDaysAction(int id) {
		this.id = id;
	}

	private int getId() {
		return this.id;
	}

	public static WeekDaysAction parseInt(int choice) {
		for (WeekDaysAction action : WeekDaysAction.values()) {
			if (action.getId() == choice)
				return action;
		}
		return null;
	}

}
