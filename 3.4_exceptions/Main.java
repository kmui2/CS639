import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Main execution class for exceptions exercise. Prompts user for username and
 * password to lookup in the accompanying sqlite3 database.
 * 
 * @author Joseph Eichenhofer
 *
 */
public class Main {

	private static final String DB_URL = "jdbc:sqlite:users.db";

	/**
	 * Prompt user for username and password. Displays login success or failure
	 * based on lookup in user database.
	 * 
	 * @param args
	 *            n/a
	 */
	public static void main(String[] args) {
		Console terminal = System.console();

		if (terminal == null) {
			System.out.println("Error fetching console. Are you running from an IDE?");
			System.exit(-1);
		}

		while (true) {
			// get username and password from user
			String username = terminal.readLine("username: ");
			if (username == null || username.toLowerCase().equals("exit"))
				break;
			String password = terminal.readLine("password: ");

			// check username and password
			if (checkPW(username, password))
				System.out.println("Login Successful! Welcome " + username);
			else
				System.out.println("Login Failure.");

			// separate iterations for repeated attempts
			System.out.println();
		}
	}

	/**
	 * Connect to the sample database and check the supplied username and password.
	 * 
	 * @param username
	 *            username to check
	 * @param password
	 *            password to check for given username
	 * @return true iff the database has an entry matching username and password
	 */
	private static boolean checkPW(String username, String password) {
		// declare database resources
		Connection c = null;
		Statement statement = null;
		ResultSet results = null;

		boolean success = true;

		String sqlQuery = "SELECT COUNT(*) AS count FROM USERS WHERE username == '" + username + "' AND password == '"
				+ password + "'";

		try {
			// connect to the database
			c = DriverManager.getConnection(DB_URL);

			// check for the username/password in database
			statement = c.createStatement();
			results = statement.executeQuery(sqlQuery);

			// if no user with that username/password, return false
			if (results.getInt("count") == 0)
				return false;

		} catch (SQLException ex) {
			// sql error, debug info:
			System.err.print("QUERY:\t");
			System.err.println(sqlQuery);
			ex.printStackTrace(System.err);
		}
		
		// cleanup sql objects
		try { results.close(); } catch (SQLException ex) { }
		try { statement.close(); } catch (SQLException ex) { }
		try { c.close(); } catch (SQLException ex) { }

		return success;
	}
}
