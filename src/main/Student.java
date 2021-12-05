package main;

import java.sql.*;
import java.util.Scanner;

import static main.JDBC.printResultSetFromSession;
import static main.JDBC.printResultSetFromTutor;

public class Student {

    private static Connection conn = null;
    private static Statement statement = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    public static void promptStudent(Connection conn, Scanner sc) throws SQLException {

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

}
