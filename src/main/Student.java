package main;

import java.sql.*;
import java.util.Scanner;

import static main.JDBC.printResultSetFromStudent;
import static main.JDBC.printResultSetFromSession;
import static main.JDBC.printResultSetFromTutor;

public class Student {

    private static Connection conn = null;
    private static Statement statement = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    public static void promptStudent(Connection conn, Scanner sc) throws SQLException {

        int studentID = 0, tutorID, grade, assignmentID, duration;
        String firstName, lastName, email, major, language, courseCode, startTime;

        int instructionType;
        String sql;

        initialLoop:
        while (true) {
            System.out.println("Type 1 = register or 2 = log in with studentID");
            instructionType = Integer.parseInt(sc.nextLine());
            statement = conn.createStatement();

            switch (instructionType) {

                case 1: //register
                    sql = "CALL Student_register (?, ?, ?, ?, ?)";
                    preparedStatement = conn.prepareStatement(sql);

                    System.out.println("First name: ");
                    firstName = sc.nextLine();
                    preparedStatement.setString(1, firstName);

                    System.out.println("Last name: ");
                    lastName = sc.nextLine();
                    preparedStatement.setString(2, lastName);

                    System.out.println("Email: ");
                    email = sc.nextLine();
                    preparedStatement.setString(3, email);

                    System.out.println("Grade: ");
                    grade = Integer.parseInt(sc.nextLine());
                    preparedStatement.setInt(4, grade);

                    System.out.println("Major: ");
                    major = sc.nextLine();
                    preparedStatement.setString(5, major);

                    preparedStatement.execute();

                    //get newly created studentID
                    statement = conn.createStatement();
                    sql = "CALL Student_get_ID (?)";
                    preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setString(1, email);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next())
                        studentID = resultSet.getInt("studentID");

                    break initialLoop;

                case 2: //log in
                    System.out.println("StudentID: ");
                    studentID = Integer.parseInt(sc.nextLine());
                    break initialLoop;

                default:
                    System.out.println("Need valid input: ");
                    break;

            }
        }

        System.out.println("Logged in with studentID: " + studentID);

        actionLoop:
        while (true) {

            System.out.println("\n1 = view summary, 2 = find tutor, 3 = request tutor, 4 = view upcoming sessions");
            System.out.println("5 = update email, 6 = update grade, 7 = update major, 8 = add course, 9 = add language, ? = type other number to quit");
            instructionType = Integer.parseInt(sc.nextLine());

            statement = conn.createStatement();

            switch (instructionType) {

                case 1: //summary
                    sql = String.format("CALL Student_summary (%d)", studentID);
                    preparedStatement = conn.prepareStatement(sql);

                    resultSet = preparedStatement.executeQuery();
                    printResultSetFromStudent(resultSet);
                    break;

                case 2: //find tutor
                    sql = "CALL Student_filter_tutors (?, ?)";
                    preparedStatement = conn.prepareStatement(sql);

                    System.out.println("Course code: ");
                    courseCode = sc.nextLine();
                    preparedStatement.setString(1, courseCode);

                    System.out.println("Language: ");
                    language = sc.nextLine();
                    preparedStatement.setString(2, language);

                    resultSet = preparedStatement.executeQuery();
                    printResultSetFromTutor(resultSet);
                    break;

                case 3: //request tutor
                    sql = "CALL Student_request_tutor (?, ?, ?, ?, ?)";
                    preparedStatement = conn.prepareStatement(sql);

                    preparedStatement.setInt(1, studentID);

                    System.out.println("TutorID");
                    tutorID = Integer.parseInt(sc.nextLine());
                    preparedStatement.setInt(2, tutorID);

                    System.out.println("AssignmentID: ");
                    assignmentID = Integer.parseInt(sc.nextLine());
                    preparedStatement.setInt(3, assignmentID);

                    System.out.println("Duration: ");
                    duration = Integer.parseInt(sc.nextLine());
                    preparedStatement.setInt(4, duration);

                    System.out.println("StartTime: ");
                    startTime = sc.nextLine();
                    preparedStatement.setString(5, startTime);

                    preparedStatement.execute();
                    break;

                case 4: //view upcoming sessions
                    sql = "CALL Student_view_upcoming_sessions (?)";
                    preparedStatement = conn.prepareStatement(sql);

                    preparedStatement.setInt(1, studentID);

                    resultSet = preparedStatement.executeQuery();
                    printResultSetFromSession(resultSet);
                    break;

                case 5:
                    sql = "CALL Student_update_email (?, ?)";
                    preparedStatement = conn.prepareStatement(sql);

                    preparedStatement.setInt(1, studentID);

                    System.out.println("Email: ");
                    email = sc.nextLine();
                    preparedStatement.setString(2, email);

                    resultSet = preparedStatement.executeQuery();
                    break;

                case 6:
                    sql = "CALL Student_update_grade (?, ?)";
                    preparedStatement = conn.prepareStatement(sql);

                    preparedStatement.setInt(1, studentID);

                    System.out.println("Grade: ");
                    grade = Integer.parseInt(sc.nextLine());
                    preparedStatement.setInt(2, grade);

                    resultSet = preparedStatement.executeQuery();
                    break;

                case 7:
                    sql = "CALL Student_update_major (?, ?)";
                    preparedStatement = conn.prepareStatement(sql);

                    preparedStatement.setInt(1, studentID);

                    System.out.println("Major: ");
                    major = sc.nextLine();
                    preparedStatement.setString(2, major);

                    resultSet = preparedStatement.executeQuery();
                    break;

                case 8:
                    sql = "CALL Student_add_course (?, ?)";
                    preparedStatement = conn.prepareStatement(sql);

                    preparedStatement.setInt(1, studentID);

                    System.out.println("Course code: ");
                    courseCode = sc.nextLine();
                    preparedStatement.setString(2, courseCode);

                    resultSet = preparedStatement.executeQuery();
                    break;

                case 9:
                    sql = "CALL Student_add_language (?, ?)";
                    preparedStatement = conn.prepareStatement(sql);

                    preparedStatement.setInt(1, studentID);

                    System.out.println("Language: ");
                    language = sc.nextLine();
                    preparedStatement.setString(2, language);

                    resultSet = preparedStatement.executeQuery();
                    break;


                default:
                    System.out.println("Quittting...");
                    break actionLoop;

            }


        }

    }
}


