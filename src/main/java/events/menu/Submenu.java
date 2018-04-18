package events.menu;

import java.io.IOException;
import java.sql.SQLException;
import user.PasswordException;
import user.UsernameException;

public interface Submenu {
	void updateDataMenu() throws SQLException, ClassNotFoundException, UsernameException, PasswordException, InterruptedException;
	void deleteDataMenu() throws SQLException, ClassNotFoundException, UsernameException, PasswordException, InterruptedException, IOException;
	void selectDataMenu() throws ClassNotFoundException, SQLException, UsernameException, PasswordException, InterruptedException;
}
