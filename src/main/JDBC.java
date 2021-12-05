package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static main.Student.promptStudent;

public class JDBC {

	static final String DB_URL = "jdbc:mysql://localhost:3306/appointmentscheduler?serverTimezone=UTC";
	static final String USER = "root";
	static final String PASS = "rootPassword";

	private static Connection conn = null;
	private static Statement statement = null;
	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		try {

			//Everything goes here
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			System.out.println("Input type of user (student, tutor, manager): ");
			String userType = sc.nextLine();

			switch (userType) {

				case "student":
					promptStudent(conn, sc);
					break;

				case "tutor":
					promptTutor();
					break;

				case "manager":
					promptManager();
					break;

				default: System.out.println("Invalid, restart program");
					break;
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

	private static void promptTutor() {

	}

	private static void promptManager() {

	}

	public static void printResultSetFromTutor (ResultSet rs) throws SQLException {

		while (rs.next()) {

			int tutorID = rs.getInt("tutorID");
			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			String email = rs.getString("email");

			System.out.println("tutorID: " + tutorID + "| name: " + firstName + " " + lastName + "| email: " + email);

		}

	}

	public static void printResultSetFromSession (ResultSet rs) throws SQLException {

		while (rs.next()) {

			int sessionID = rs.getInt("sessionID");
			int studentID = rs.getInt("studentID");
			int tutorID = rs.getInt("tutorID");
			int assignmentID = rs.getInt("assignmentID");
			int duration = rs.getInt("duration");
			String startTime = rs.getString("startTime");

			System.out.println("sessionID: " + sessionID + "| studentID: " + studentID + "| tutorID: " + tutorID
					+ "| assignmentID: " + assignmentID + "| duration: " + duration + "| startTime: " + startTime);

		}

	}

}
