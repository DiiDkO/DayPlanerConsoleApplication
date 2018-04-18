package events.menu;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import database.manager.DatabaseManager;
import database.selector.EventSelector;
import delete.submenu.options.DeleteSubmenuOptions;
import event.Event;
import insert.event.option.InputEventData;
import jdb.connenction.JDBConnector;
import login.register.menu.LoginRegisterMenu;
import select.submenu.options.SelectSubmenuOptions;
import time.table.renderer.TimeTableRenderer;
import update.data.submenu.options.UpdatesubMenuOptions;
import user.InputUserData;
import user.PasswordException;
import user.User;
import user.UsernameException;

public class Menu implements Submenu {
	Scanner in = new Scanner(System.in);
	private User user;
	private LocalDate localDate;
	private Date date;

	public Menu(User user)
			throws SQLException, ClassNotFoundException, UsernameException, PasswordException, InterruptedException {
		this.user = user;
		date = new Date();
		localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		this.displayMenu();
		InputMenuOption menuOption = inputMenuOption();
		this.menuOptions(menuOption);
	}

	private InputMenuOption inputMenuOption() {
		try {
			return pickMenuOption();
		} catch (NumberFormatException e) {
			System.out.println("\nInvalid input!\nInput valid option!");
			System.out.print("Select: ");
			return inputMenuOption();
		}
	}

	private InputMenuOption pickMenuOption() {
		InputMenuOption option = InputMenuOption.UNKNOWN;
		option = InputMenuOption.getByInt(Integer.parseInt(in.nextLine()));
		while (option.equals(InputMenuOption.UNKNOWN)) {
			System.out.println("\nInvalid input!\nInput valid option!");
			System.out.print("Select: ");
			option = InputMenuOption.getByInt(Integer.parseInt(in.nextLine()));
		}
		return option;
	}

	private void menuOptions(InputMenuOption menuOption)
			throws SQLException, ClassNotFoundException, UsernameException, PasswordException, InterruptedException {

		switch (menuOption) {
		case ADD:
			this.displayAddSubmenu();
			this.addDataMenu();
			this.questionForMenu();
			break;
		case UPDATE:
			this.displayUpdateSubmenu();
			this.updateDataMenu();
			this.questionForMenu();
			break;
		case DELETE:
			this.displayDeleteSubmenu();
			this.deleteDataMenu();
			this.questionForMenu();
			break;
		case SELECT:
			this.displaySelectSubmenu();
			this.selectDataMenu();
			this.questionForMenu();
			break;
		}
	}

	private void displayMenu() {
		System.out.println("\nSelect:\n1)Add Menu\n2)Update Menu\n3)Delete Menu\n4)Select Menu");
		System.out.print("Selection: ");
	}

	private void displayAddSubmenu() {
		System.out.println("\nSelect :\n1)Add event\n2)Add MOTD");
		System.out.print("Selection: ");
	}

	private void displayUpdateSubmenu() {
		System.out.println(
				"\nSelect:\n1)Update user's password\n2)Update event's name\n3)Update event's start date and Time\n4)Update event's end Time\n5)Update event's decription\n6)Update MOTD");
		System.out.print("Selection: ");
	}

	private void displaySelectionWeekEventsSubmenu(boolean weeks) {
		System.out.println("\nSelect:\n1)Events from first week\n2)Events from second week\n3)Events from third week\n"
				+ "4)Events from forth week" + (weeks ? ("\n" + "5)Events from fifth week") : ("")));
		System.out.println("Selection: ");
	}

	private void displayDeleteSubmenu() {
		System.out.println(
				"\nSelect:\n1)Delete user\n2)Delete all event\'s parameters\n3)Delete event\n4)Delete on events date_time\n5)DeleteMOTD");
		System.out.print("Selection: ");
	}

	public void displaySelectSubmenu() {
		System.out.println("\nSelect:\n1)Select events on certain date of the current month"
				+ "\n2)Select events on certain date of month" + "\n3)Select events from certain week of a month"
				+ "\n4)Select events from certain week of a current month" + "\n5)Select events from whole month"
				+ "\n6)Select events from whole current month" + "\n7)Select events from date to date\n");
		System.out.print("Selection: ");
	}

	private void questionForMenu()
			throws ClassNotFoundException, SQLException, UsernameException, PasswordException, InterruptedException {
		displayQuestionForMenu();
		QuestionForMenuAction option=this.inputOptionForQuestionMenu();
			
			switch (option) {
			case PROCEED:
				System.out.println("Please proceed !\n");
				new Menu(this.user);
				break;

			case QUIT:
				System.out.println("Good-bye! See you soon !\n");
				break;
		}
	}

	private void displayQuestionForMenu() {
		System.out.println("\nWould you like to proceed or quit?");
		System.out.println("To proceed press 9");
		System.out.println("To quit press 0\n");
		System.out.print("Selection: ");
	}
	private QuestionForMenuAction inputOptionForQuestionMenu() throws ClassNotFoundException, SQLException, UsernameException, PasswordException, InterruptedException {
		try {
			return this.pickOptionQuestionMenu();
		}
		catch (NumberFormatException e) {
			System.out.println("\nInvalid input!\nInput valid option!");
			System.out.print("Select: ");
			return this.inputOptionForQuestionMenu();
		}
	}
	private QuestionForMenuAction pickOptionQuestionMenu()
			throws ClassNotFoundException, SQLException, UsernameException, PasswordException, InterruptedException {
		QuestionForMenuAction option = QuestionForMenuAction.UNKNOWN;
		option = QuestionForMenuAction.parseInt(Integer.parseInt(in.nextLine()));
		while (option.equals(QuestionForMenuAction.UNKNOWN)) {
			System.out.println("\nInvalid input!\nInput valid option!");
			System.out.print("Select: ");
			option = QuestionForMenuAction.parseInt(Integer.parseInt(in.nextLine()));
		}
		return option;
	}

	public void addDataMenu()
			throws SQLException, ClassNotFoundException, UsernameException, PasswordException, InterruptedException {

		SubmenuAction action = inputSubmenuAction(InputMenuOption.ADD);
		System.out.println();
		switch (action) {
		case ADD_EVENT:
			this.addEvent();
			break;
		case ADD_MOTD:
			this.addMOTD();
			break;
		}
	}

	private void addMOTD() throws SQLException, ClassNotFoundException {
		try (Connection conn = new JDBConnector().getConnection()) {
			DatabaseManager insert = new DatabaseManager(conn);
			Event event = InputEventData.inputMOTD();
			String username = this.user.getUsername();
			insert.insertEvent(event, username);
			System.out.println("MOTD is added !\n");
		}
	}

	private void addEvent() throws SQLException, ClassNotFoundException {
		try (Connection conn = new JDBConnector().getConnection()) {
			DatabaseManager insert = new DatabaseManager(conn);
			InputEventData inputEventData = new InputEventData();
			Event event = inputEventData.inputEvent();
			String username = this.user.getUsername();
			insert.insertEvent(event, username);
			System.out.println("Event is added !\n");
		}
	}

	@SuppressWarnings("incomplete-switch")
	public void updateDataMenu()
			throws SQLException, ClassNotFoundException, UsernameException, PasswordException, InterruptedException {

		SubmenuAction action = inputSubmenuAction(InputMenuOption.UPDATE);

		System.out.println();
		switch (action) {
		case UPDATE_USER_PASSWORD:
			this.updateUserPassword();
			break;
		case UPDATE_EVENT_NAME:
			this.updateEventName();
			break;
		case UPADATE_EVENT_START_DATE_TIME:
			this.updateEventStartDateTime();
			break;
		case UPDATE_EVENT_END_TIME:
			this.updateEventEndDateTime();
			break;
		case UPDATE_EVENT_DISCRIPTION:
			this.updateEventDescription();
		case UPDATE_EVENT_MOTD:
			this.updateMOTD();
			break;
		}
	}

	private SubmenuAction inputSubmenuAction(InputMenuOption menu) {
		try {
			return pickMenuOption(menu);
		} catch (NumberFormatException e) {
			System.out.println("\nInvalid input!\nInput valid option!");
			System.out.print("Select: ");
			return inputSubmenuAction(menu);
		}
	}

	private SubmenuAction pickMenuOption(InputMenuOption menu) {
		SubmenuAction action = SubmenuAction.UNKNOWN;
		action = SubmenuAction.fromMenuAndChoice(menu, Integer.parseInt(in.nextLine()));
		while (action.equals(SubmenuAction.UNKNOWN)) {
			System.out.println("\nInvalid input!\nInput valid option!");
			System.out.print("Select: ");
			action = SubmenuAction.fromMenuAndChoice(menu, Integer.parseInt(in.nextLine()));
		}
		return action;
	}

	private void updateMOTD() throws ClassNotFoundException, SQLException {
		try (Connection connUpdateEventD = new JDBConnector().getConnection()) {
			DatabaseManager updateEventD = new DatabaseManager(connUpdateEventD);
			Event MOTD = new InputEventData().getEventNameStarEndDateTime();
			String updateMOTD = new UpdatesubMenuOptions().updateEventDescription();
			String username = this.user.getUsername();
			if (updateEventD.updateEventDescription(updateMOTD, MOTD, username))
				System.out.println("MOTD updated successfully !\n");
			else
				System.out.println("Error in updating MOTD !\n");
		}
	}

	private void updateEventDescription() throws ClassNotFoundException, SQLException {
		try (Connection connUpdateEventD = new JDBConnector().getConnection()) {
			DatabaseManager updateEventD = new DatabaseManager(connUpdateEventD);
			Event event = new InputEventData().getEventNameStarEndDateTime();
			String updateEventDescription = new UpdatesubMenuOptions().updateEventDescription();
			String username = this.user.getUsername();
			if (updateEventD.updateEventDescription(updateEventDescription, event, username))
				System.out.println("Event decription updated successfully !\n");
			else
				System.out.println("Error in updating event description !\n");
		}
	}

	private void updateEventStartDateTime() throws ClassNotFoundException, SQLException {
		try (Connection connUpdateEventDT = new JDBConnector().getConnection()) {
			DatabaseManager updateEventDT = new DatabaseManager(connUpdateEventDT);
			Event event = new InputEventData().getEventNameStarEndDateTime();
			Timestamp startDTime = new UpdatesubMenuOptions().updateStartTime();
			String username = this.user.getUsername();
			if (updateEventDT.updateEventStartTime(startDTime, event, username))
				System.out.println("Event start dateTime updating successfully!\n");
			else
				System.out.println("Error in updating event dateTime !\n");
		}
	}

	private void updateEventEndDateTime() throws ClassNotFoundException, SQLException {
		try (Connection connUpdateEventDT = new JDBConnector().getConnection()) {
			DatabaseManager updateEventDT = new DatabaseManager(connUpdateEventDT);
			Event inputEvent = new InputEventData().getEventNameStarEndDateTime();
			int startHour = inputEvent.getStartTime().toLocalDateTime().getHour();
			LocalDate date = inputEvent.getStartTime().toLocalDateTime().toLocalDate();
			if (updateEventDT.updateEventEndTime(new UpdatesubMenuOptions().updateEndTime(date, startHour), inputEvent,
					this.user.getUsername()))
				System.out.println("Event end dateTime updating successfully!\n");
			else
				System.out.println("Error in updating event end dateTime !\n");
		}
	}

	private void updateEventName() throws ClassNotFoundException, SQLException {
		try (Connection connUpdateEvent = new JDBConnector().getConnection()) {
			DatabaseManager updateEvent = new DatabaseManager(connUpdateEvent);
			Event event = new InputEventData().getEventNameStarEndDateTime();
			String updateEventName = new UpdatesubMenuOptions().updateEventName();
			String username = this.user.getUsername();
			if (updateEvent.updateEventName(updateEventName, event, username))
				System.out.println("Event name updated successfully !\n");
			else
				System.out.println("Error in updating event name !\n");
		}
	}

	private void updateUserPassword() throws SQLException, ClassNotFoundException {
		try (Connection connUpdatePass = new JDBConnector().getConnection()) {
			DatabaseManager updatePass = new DatabaseManager(connUpdatePass);
			String newPassword = new UpdatesubMenuOptions().updateUserPassword(this.user);
			if (updatePass.updateUserPassword(newPassword, this.user))
				System.out.println("Password is successfully changed !\n");
			else
				System.out.println("Error in updating password!\n");
		}
	}

	public void deleteDataMenu()
			throws SQLException, ClassNotFoundException, UsernameException, PasswordException, InterruptedException {

		SubmenuAction action = this.inputSubmenuAction(InputMenuOption.DELETE);
		System.out.println();
		DeleteSubmenuOptions delSubmenuOpt = new DeleteSubmenuOptions();

		switch (action) {
		case DELETE_EVENT_USER:
			try {
				this.deleteUser();
			} catch (InterruptedException e1) {
				System.out.println("Invalid user's data !");
			}
			break;
		case DELETE_ALL_EVENTS:
			this.deleteAllEvents();
			break;
		case DELETE_EVENT:
			this.deleteEvent(delSubmenuOpt);
			break;
		case DELETE_EVNET_ON_DATE_TIME:
			this.deleteEventOnDateTime(delSubmenuOpt);
			break;
		case DELETE_MOTD:
			this.deleteMOTD();
		break;
		}

	}

	private void deleteEventOnDateTime(DeleteSubmenuOptions delSubmenuOpt) throws ClassNotFoundException, SQLException {
		try (Connection connDelEventDT = new JDBConnector().getConnection()) {

			DatabaseManager deleteEventDT = new DatabaseManager(connDelEventDT);
			Timestamp startDateTime = delSubmenuOpt.deleteStartDateTime();
			LocalDate date = startDateTime.toLocalDateTime().toLocalDate();
			String username = delSubmenuOpt.comfirmUser(this.user).getUsername();
			@SuppressWarnings("deprecation")
			Timestamp endDateTime = delSubmenuOpt.deleteEndDateTime(date, startDateTime.getHours());
			if (deleteEventDT.deleteOnDateTime(startDateTime, endDateTime, username))
				System.out.println("Events with chosen dateTime are deleted !\n");
			else
				System.out.println("Error in deleting events !\n");
		}
	}

	private void deleteEvent(DeleteSubmenuOptions delSubmenuOpt) throws ClassNotFoundException, SQLException {
		try (Connection connDelEvent = new JDBConnector().getConnection()) {
			DatabaseManager deleteEvent = new DatabaseManager(connDelEvent);
			Event event = delSubmenuOpt.getEvent();
			String username = delSubmenuOpt.comfirmUser(this.user).getUsername();
			if (deleteEvent.deleteEvent(event, username))
				System.out.println("The event is deleted !\n");
			else
				System.out.println("Error in deleting the event !\n");
		}
	}

	private void deleteMOTD() throws ClassNotFoundException, SQLException {
		try (Connection connDelEvent = new JDBConnector().getConnection()) {
			DatabaseManager deleteEvent = new DatabaseManager(connDelEvent);
			Event MOTD = new DeleteSubmenuOptions().deleteMOTD();
			String username = new DeleteSubmenuOptions().comfirmUser(this.user).getUsername();
			if (deleteEvent.deleteEvent(MOTD, username))
				System.out.println("The MOTD is deleted !\n");
			else
				System.out.println("Error in deleting the MOTD !\n");
		}
	}

	private void deleteAllEvents() throws ClassNotFoundException, SQLException {
		try (Connection connDelAllEvents = new JDBConnector().getConnection()) {
			DatabaseManager deleteAllEvents = new DatabaseManager(connDelAllEvents);
			String username = new DeleteSubmenuOptions().comfirmUser(this.user).getUsername();
			if (deleteAllEvents.deleteAllEvent(username))
				System.out.println("All events are deleted !\n");
			else
				System.out.println("Error in deleting the events !\n");
		}
	}

	private void deleteUser()
			throws ClassNotFoundException, SQLException, UsernameException, PasswordException, InterruptedException {
		try (Connection connDelUser = new JDBConnector().getConnection()) {
			DatabaseManager deleteU = new DatabaseManager(connDelUser);
			String username = new DeleteSubmenuOptions().comfirmUser(this.user).getUsername();
			if (deleteU.deleteUser(username)) {
				System.out.println("User is deleted !\n");
				LoginRegisterMenu log = new LoginRegisterMenu();
				new Menu(log.getUser());
			} else
				System.out.println("Error in deleting user !\n");
		}
	}

	public void selectDataMenu()
			throws ClassNotFoundException, SQLException, UsernameException, PasswordException, InterruptedException {
		SubmenuAction action = this.inputSubmenuAction(InputMenuOption.SELECT);
		switch (action) {
		case SELECT_EVENTS_FROM_DAY_FROM_CURRENET_MONTH:
			this.selectDayFromCurrentMonth();
			break;
		case SELECT_EVENTS_FROM_DAY_FROM_MONTH:
			this.selectDayFromMonth();
			break;
		case SELECT_EVENTS_FROM_WEEK_FROM_CURRENT_MONTH:
			this.selectEventsFromnWeekFromMonth();
			break;
		case SELECT_EVENTS_FROM_WEEK_FROM_MONTH:
			this.selectEventsFromWeekFromCurrentMonth();
			break;
		case SELECT_EVENTS_FROM_CURRENT_MONTH:
			this.selectEventsFromMonth();
			break;
		case SELECT_EVENT_FROM_MONTH:
			this.selectEventsFromCurrentMonth();
			break;
		case SELECT_EVENTS_FROM_DATE_TO_DATE:
			this.selectEventsFromDateToDate();
			break;
		}
	}

	private void selectEventsFromDateToDate() throws SQLException, ClassNotFoundException {

		try (Connection connSelectEventsFromDateToDate = new JDBConnector().getConnection()) {
			List<Event> list = new ArrayList<>();
			SelectSubmenuOptions selectSm= new SelectSubmenuOptions();
			EventSelector selectorEventsFromDateToDate = new EventSelector(connSelectEventsFromDateToDate);
			LocalDate fromDate = selectSm.inputFrom_ToData(true);
			LocalDate toDate = selectSm.inputFrom_ToData(false);
			String username = this.user.getUsername();
			list = selectorEventsFromDateToDate.selectDataFromDateToDate(fromDate, toDate, username);
			TimeTableRenderer print = new TimeTableRenderer();
			print.printEventsFromDateToDate(fromDate, toDate, list,connSelectEventsFromDateToDate,username);
		}
	}

	private void selectEventsFromWeekFromCurrentMonth()
			throws SQLException, ClassNotFoundException {
		try (Connection connSelectEventsFromWeekFromCurrentMonth = new JDBConnector().getConnection()) {
			List<Event> list = new ArrayList<>();
			SelectSubmenuOptions selectSm= new SelectSubmenuOptions();
			String username = this.user.getUsername();
			EventSelector selectorEventsFromWeekFromCurrentMonth = new EventSelector(
					connSelectEventsFromWeekFromCurrentMonth);
			this.displaySelectionWeekEventsSubmenu(selectSm.numberOfWeek(
					selectSm.display_numberOfWeeks(this.localDate.getMonthValue(), this.localDate.getYear())));
			int weekOfMonth = selectSm.selectWeekOfMonth();
			list = selectorEventsFromWeekFromCurrentMonth.selectDataFortWeekOfCurrentMonth(weekOfMonth, username);
			TimeTableRenderer printWeek = new TimeTableRenderer();
			printWeek.printWeek(weekOfMonth, this.localDate.getMonthValue(), this.localDate.getYear(), list,connSelectEventsFromWeekFromCurrentMonth,username);
		}
	}

	private void selectEventsFromnWeekFromMonth()
			throws SQLException, ClassNotFoundException {
		try (Connection connSelectEventsFromnWeekFromMonth = new JDBConnector().getConnection()) {
			List<Event> list = new ArrayList<>();
			SelectSubmenuOptions selectSm= new SelectSubmenuOptions();
			int month = selectSm.inputMonth();
			int year = selectSm.inputYear();
			String username = this.user.getUsername();
			EventSelector selectorEventsFromnWeekFromMonth = new EventSelector(connSelectEventsFromnWeekFromMonth);
			this.displaySelectionWeekEventsSubmenu(selectSm.numberOfWeek(selectSm.display_numberOfWeeks(month, year)));
			int weekOfMonth = selectSm.selectWeekOfMonth();
			list = selectorEventsFromnWeekFromMonth.selectDataFortWeekOfMonth(month, year, weekOfMonth, username);
			TimeTableRenderer printWeek = new TimeTableRenderer();
			printWeek.printWeek(weekOfMonth, month, year, list,connSelectEventsFromnWeekFromMonth,username);
		}
	}

	private void selectEventsFromCurrentMonth() throws SQLException, ClassNotFoundException {

		try (Connection connSelectEventsFromCurrMonth = new JDBConnector().getConnection()) {
			List<Event> list = new ArrayList<>();
			String username = this.user.getUsername();
			EventSelector selectorEventsFromCurrMonth = new EventSelector(connSelectEventsFromCurrMonth);
			list = selectorEventsFromCurrMonth.selectAllDataFromCurrentMonth(username);
			LocalDate dateNow = LocalDate.now();
			LocalDate date = LocalDate.of(dateNow.getYear(), dateNow.getMonthValue(), 1);
			TimeTableRenderer printDaysOfMonth = new TimeTableRenderer();
			printDaysOfMonth.printDaysOfMonth(date, list,connSelectEventsFromCurrMonth,this.user.getUsername());
		}

	}

	private void selectEventsFromMonth() throws SQLException, ClassNotFoundException {

		try (Connection connSelectEventsFromMoth = new JDBConnector().getConnection()) {
			List<Event> list = new ArrayList<>();
			EventSelector selectorEventsFromMoth = new EventSelector(connSelectEventsFromMoth);
			int month = new SelectSubmenuOptions().inputMonth();
			int year = new SelectSubmenuOptions().inputYear();
			String username = this.user.getUsername();
			list = selectorEventsFromMoth.selectAllDataFromMonth(month, year, username);
			LocalDate date = LocalDate.of(year, month, 1);
			TimeTableRenderer printDaysOfMonth = new TimeTableRenderer();
			printDaysOfMonth.printDaysOfMonth(date, list,connSelectEventsFromMoth,this.user.getUsername());
		}
	}

	private void selectDayFromMonth() throws SQLException, ClassNotFoundException {

		try (Connection connSelectDayFromMonth = new JDBConnector().getConnection()) {
			EventSelector selectorDayFromMonth = new EventSelector(connSelectDayFromMonth);
			List<Event> list = new ArrayList<>();
			LocalDate dateNow = LocalDate.now();
			int month = new SelectSubmenuOptions().inputMonth();
			int day = new SelectSubmenuOptions().inputDay(month, dateNow.getYear());
			String username = this.user.getUsername();
			list = selectorDayFromMonth.selectDataForDayFromMonth(day, month, username);
			LocalDate date = LocalDate.of(dateNow.getYear(), month, day);
			TimeTableRenderer print = new TimeTableRenderer();
			print.printDay(date, list,connSelectDayFromMonth,this.user.getUsername());
		}
	}

	private void selectDayFromCurrentMonth() throws SQLException, ClassNotFoundException {

		try (Connection connSelectDayFromCurrentMonth = new JDBConnector().getConnection()) {
			EventSelector selectorDayFromCurrMonth = new EventSelector(connSelectDayFromCurrentMonth);
			List<Event> list = new ArrayList<>();
			LocalDate dateNow = LocalDate.now();
			String username = this.user.getUsername();
			int day = new SelectSubmenuOptions().inputDay(dateNow.getMonthValue(), dateNow.getYear());
			list = selectorDayFromCurrMonth.selectDataForDayFromCurrecntMonth(day, username);
			TimeTableRenderer print = new TimeTableRenderer();
			LocalDate date = LocalDate.of(dateNow.getYear(), dateNow.getMonthValue(), day);
			print.printDay(date, list,connSelectDayFromCurrentMonth,this.user.getUsername());
		}
	}
}
