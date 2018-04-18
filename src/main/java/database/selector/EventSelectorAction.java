package database.selector;

public enum EventSelectorAction {
	FIRSTWEEK(1), SECONDWEEK(2), THIRDWEEK(3), FORTHWEEK(4), FIFTHWEEK(5),UNKNOWN(0);

	private int id;

	EventSelectorAction(int id) {
		this.id = id;
	}

	private int getId() {
		return this.id;
	}

	public static EventSelectorAction parseInt(int choice) {
		for (EventSelectorAction action : EventSelectorAction.values()) {
			if (action.getId() == choice)
				return action;
		}
		return UNKNOWN;
	}
}
