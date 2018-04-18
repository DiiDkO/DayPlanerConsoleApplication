package MyDayPlanner.DayPlaner;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import events.menu.Menu;
import login.register.menu.LoginRegisterMenu;
import user.PasswordException;
import user.User;
import user.UsernameException;

/**
 * Hello world!
 *
 */
public class App {
	Scanner in = new Scanner(System.in);
	Scanner in1 = new Scanner(System.in);
	public static User loggedinUser;
	public App() throws ClassNotFoundException, SQLException, UsernameException, PasswordException,
			InterruptedException, IOException {
		LoginRegisterMenu logReg = new LoginRegisterMenu();
		loggedinUser = logReg.getUser();
		new Menu(loggedinUser);
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException, UsernameException, PasswordException, InterruptedException {
		try {

			new App();
		} catch (InterruptedException e) {
			System.out.println("Error in execution !");
		} catch (UsernameException e) {
			System.out.println(e.getMessage());
		} catch (PasswordException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Error in system!");
		} catch (SQLException e) {
			System.out.println("Database's data  is incorrect or duplicated!");
		} catch (IOException e) {
			System.out.println("IO system error!");
		}
	}

}
