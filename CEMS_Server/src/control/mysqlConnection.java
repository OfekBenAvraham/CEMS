package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import gui.ServerPortController;

/**
 * This class provides functionalities to establish a connection with a MySQL database.
 */
public class mysqlConnection {
	public static Connection conn;

    /**
     * Establishes a connection to the database with the provided password.
     * 
     * @param password The password to connect to the database.
     */
	public static void connecttoDB(String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
		}

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/cems?serverTimezone=Asia/Jerusalem", "root", password);
			System.out.println("SQL connection succeed");
			ServerPortController.server.connectionToDBSuccessfull = true;
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			ServerPortController.server.connectionToDBSuccessfull = false;
		}
		ServerPortController.serverTryToConnectDB = false;
	}

}
