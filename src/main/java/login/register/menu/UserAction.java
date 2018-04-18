package login.register.menu;

public enum UserAction {

	LOGIN(1), REGISTRATION(2), UNKNOWN(0);
	private int option;

	UserAction(int option) {
		this.option = option;
	}

	public int getInt() {
		return this.option;
	}

	public static UserAction parseInt(int option) {
		for (UserAction action : UserAction.values())
			if (action.getInt() == option)
				return action;
		return UNKNOWN;
	}

}
