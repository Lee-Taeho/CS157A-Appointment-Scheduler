import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBC {

	static final String DB_URL = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
	static final String USER = "root";
	static final String PASS = "rootPassword";

	private static Connection conn = null;
	private static Statement statement = null;
	
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		try {

			//Everything goes here
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String userType = getUserType();
			
			if (userType.equals("student")) {
				promptStudent();
			}
			
			else if (userType.equals("tutor")) {
				promptTutor();
			}

			else if (userType.equals("manager")) {
				promptManager();
			}
			
			
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		finally {

			sc.close();
			
			try {
				if (statement != null) statement.close(); } 
			catch (SQLException se) { }
			try {
				if (conn != null) conn.close(); }
			catch (SQLException se) {
				se.printStackTrace(); }

		}

	}
	
	private static String getUserType() {
		return sc.nextLine();
	}
	
	private static void promptStudent() {
		
	}
	
	private static void promptTutor() {
		
	}
	
	private static void promptManager() {
		
	}

}
