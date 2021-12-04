import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBC {

	static final String DB_URL = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
	static final String USER = "root";
	static final String PASS = "th990307";

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
			String userType = getUserType();
			
			switch (userType) {
			
				case "student": 
					promptStudent();
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
	
	private static String getUserType() {
		return sc.nextLine();
	}
	
	private static void promptStudent() throws SQLException{
		
		outerloop:
		while (true) {
			
			System.out.println("Input what you would like to do: ");
			System.out.println("1 = register, 2 = find tutor, 3 = request tutor, 4 = view upcoming sessions, ? = quit with invalid value");
			int instructionType = Integer.valueOf(sc.nextLine());
			String sql = "";
			statement = conn.createStatement();
			
			switch (instructionType) {
			
			case 1:
				sql = "CALL Student_register (?, ?, ?, ?, ?)";
				preparedStatement = conn.prepareStatement(sql);
				
				System.out.println("First name: ");
				String firstName = sc.nextLine();
				preparedStatement.setString(1, firstName);
				
				System.out.println("Last name: ");
				String lastName = sc.nextLine();
				preparedStatement.setString(2, lastName);
				
				System.out.println("Email: ");
				String email = sc.nextLine();
				preparedStatement.setString(3, email);
				
				System.out.println("Grade: ");
				int grade = Integer.valueOf(sc.nextLine());
				preparedStatement.setInt(4, grade);
				
				System.out.println("Major: ");
				String major = sc.nextLine();
				preparedStatement.setString(5, major);
				
				preparedStatement.execute();
				break;
			
			case 2: 
				sql = "CALL Student_filter_tutors (?, ?)";
				preparedStatement = conn.prepareStatement(sql);
				
				System.out.println("Course code: ");
				String courseCode = sc.nextLine();
				preparedStatement.setString(1, courseCode);
				
				System.out.println("Language: ");
				String language = sc.nextLine();
				preparedStatement.setString(2, language);
				
				resultSet = preparedStatement.executeQuery();
				printResultSetFromTutor(resultSet);
				break;
				
			case 3:
				sql = "CALL Student_request_tutor (?, ?, ?, ?, ?)";
				preparedStatement = conn.prepareStatement(sql);
				
				System.out.println("StudentID: ");
				int studentID = Integer.valueOf(sc.nextLine());
				preparedStatement.setInt(1, studentID);
				
				System.out.println("TutorID");
				int tutorID = Integer.valueOf(sc.nextLine());
				preparedStatement.setInt(2, tutorID);
				
				System.out.println("AssignmentID: ");
				int assignmentID = Integer.valueOf(sc.nextLine());
				preparedStatement.setInt(3, assignmentID);
				
				System.out.println("Duration: ");
				int duration = Integer.valueOf(sc.nextLine());
				preparedStatement.setInt(4, duration);
				
				System.out.println("StartTime: ");
				String startTime = sc.nextLine();
				preparedStatement.setString(5, startTime);
				
				preparedStatement.execute();
				break;
				
			case 4:
				sql = "CALL Student_view_upcoming_sessions (?)";
				preparedStatement = conn.prepareStatement(sql);
				
				System.out.println("StudentID: ");
				int studentID2 = Integer.valueOf(sc.nextLine());
				preparedStatement.setInt(1, studentID2);
				
				resultSet = preparedStatement.executeQuery();
				printResultSetFromSession(resultSet);
				break;
				
			default:
				break outerloop;
				
			}
			
			
			
		}
		
	}
	
	private static void promptTutor() {
		
	}
	
	private static void promptManager() {
		
	}
	
	private static void printResultSetFromTutor (ResultSet rs) throws SQLException {
		
		while (rs.next()) {
			
			int tutorID = rs.getInt("tutorID");
			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			String email = rs.getString("email");
			
			System.out.println("tutorID: " + tutorID + "| name: " + firstName + " " + lastName + "| email: " + email);
			
		}
		
	}
	
	private static void printResultSetFromSession (ResultSet rs) throws SQLException {
		
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
