package events.menu;

public enum InputMenuOption {
	ADD(1),UPDATE(2), DELETE(3), SELECT(4),  UNKNOWN(0);
	
	private int option;
	
	InputMenuOption(int option){
		this.option = option;
	}
	
	private int getIntOption() {
		return option;
	}
	
	public static InputMenuOption getByInt(int option) {
		for(InputMenuOption menuOption : values()) 
			if(menuOption.getIntOption() == option)
				return menuOption;
		return UNKNOWN;
	}
}
