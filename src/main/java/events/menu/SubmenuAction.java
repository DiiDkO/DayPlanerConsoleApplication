package events.menu;

public enum SubmenuAction {
	ADD_EVENT(InputMenuOption.ADD, 1),
	ADD_MOTD(InputMenuOption.ADD, 2), 
	UPDATE_USER_PASSWORD(InputMenuOption.UPDATE, 1),
	UPDATE_EVENT_NAME(InputMenuOption.UPDATE, 2),
	UPADATE_EVENT_START_DATE_TIME(InputMenuOption.UPDATE,3),
	UPDATE_EVENT_END_TIME(InputMenuOption.UPDATE,4),
	UPDATE_EVENT_DISCRIPTION(InputMenuOption.UPDATE,5),
	UPDATE_EVENT_MOTD(InputMenuOption.UPDATE,6),
	DELETE_EVENT_USER(InputMenuOption.DELETE,1),
	DELETE_ALL_EVENTS(InputMenuOption.DELETE,2),
	DELETE_EVENT(InputMenuOption.DELETE,3),
	DELETE_EVNET_ON_DATE_TIME(InputMenuOption.DELETE,4),
	DELETE_MOTD(InputMenuOption.DELETE,5),
	SELECT_EVENTS_FROM_DAY_FROM_CURRENET_MONTH(InputMenuOption.SELECT,1),
	SELECT_EVENTS_FROM_DAY_FROM_MONTH(InputMenuOption.SELECT,2),
	SELECT_EVENTS_FROM_WEEK_FROM_CURRENT_MONTH(InputMenuOption.SELECT,3),
	SELECT_EVENTS_FROM_WEEK_FROM_MONTH(InputMenuOption.SELECT,4),
	SELECT_EVENTS_FROM_CURRENT_MONTH(InputMenuOption.SELECT,5),
	SELECT_EVENT_FROM_MONTH(InputMenuOption.SELECT,6),
	SELECT_EVENTS_FROM_DATE_TO_DATE(InputMenuOption.SELECT,7),
	UNKNOWN(null, 0);
	
	InputMenuOption menu;
	int choice;
	
	SubmenuAction(InputMenuOption menu, int choice)	{
		this.menu = menu;
		this.choice = choice;
	}
	
	public InputMenuOption getParentMenu() {
		return this.menu;
	}
	
	public int getChoice() {
		return this.choice;
	}
	
	public static SubmenuAction fromMenuAndChoice(InputMenuOption menu, int choice) {
		for(SubmenuAction action : values()) {
			if(!action.equals(UNKNOWN) && action.getParentMenu().equals(menu) && action.getChoice() == choice) {
				return action;
			}
		}
		return UNKNOWN;
	}
}
