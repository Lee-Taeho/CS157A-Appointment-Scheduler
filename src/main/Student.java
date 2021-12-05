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

        initialLoop:
        while (true) {
            System.out.println("Type 1 = register or 2 = log in with studentID");
            int instructionType = Integer.parseInt(sc.nextLine());
            String sql;
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
                    sql = String.format("CALL Student_get_ID (%s)", email);
                    resultSet = preparedStatement.executeQuery();
                    studentID = resultSet.getInt("studentID");

                    break;

                case 2: //log in
                    System.out.println("StudentID: ");
                    studentID = Integer.parseInt(sc.nextLine());
                    break;

                default:
                    System.out.println("Input what you would like to do: ");
                    break initialLoop;

            }

            System.out.println("Logged in with studentID: " + studentID);

            actionLoop:
            while (true) {

                System.out.println("1 = view summary, 2 = find tutor, 3 = request tutor, 4 = view upcoming sessions");
                System.out.println("5 = update email, 6 = update grade, 7 = update major, 8 = add course, 9 = add language, ? = type anything to quit");
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

                        System.out.println("StudentID: ");
                        studentID = Integer.parseInt(sc.nextLine());
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

                        System.out.println("StudentID: ");
                        studentID = Integer.parseInt(sc.nextLine());
                        preparedStatement.setInt(1, studentID);

                        resultSet = preparedStatement.executeQuery();
                        printResultSetFromSession(resultSet);
                        break;

                    case 5:
                        sql = "CALL Student_summary (?)";
                        preparedStatement = conn.prepareStatement(sql);

                        System.out.println("StudentID: ");
                        studentID = Integer.valueOf(sc.nextLine());
                        preparedStatement.setInt(1, studentID);

                        resultSet = preparedStatement.executeQuery();
                        printResultSetFromSession(resultSet);
                        break;


                    default:
                        System.out.println("Quittting...");
                        break actionLoop;

                }


            }

        }

    }
}
