package events.menu;

public enum QuestionForMenuAction {
	PROCEED(9), QUIT(0), UNKNOWN(1);
	private int option;
	 QuestionForMenuAction(int option) {
		this.option=option;
	}
	private int getOption() {
		return this.option;
	}
	public static QuestionForMenuAction parseInt(int option) {
		for(QuestionForMenuAction action: QuestionForMenuAction.values())
			if(action.getOption()==option)
				return action;
		return UNKNOWN;
	}
	
}
