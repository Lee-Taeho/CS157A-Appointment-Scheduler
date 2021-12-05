package main;

import java.sql.*;
import java.util.Scanner;

import static main.JDBC.*;

public class TutorManager {

    private static Connection conn = null;
    private static Statement statement = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    public static void promptManager(Connection conn, Scanner sc) throws SQLException {

        outerloop:
        while (true) {

            System.out.println("Input what you would like to do: ");
            System.out.println("1 = view schedule, 2 = update status, 3 = add courses, 4 = find tutors in course, 5 = archive sessions, ? = quit with invalid value");
            int instructionType = Integer.valueOf(sc.nextLine());
            String sql = "";
            statement = conn.createStatement();

            switch (instructionType) {

                case 1:
                    sql = "CALL TutorManager_view_schedule (?, ?)";
                    preparedStatement = conn.prepareStatement(sql);

                    System.out.println("Tutor ID: ");
                    String tutorID = sc.nextLine();
                    preparedStatement.setString(1, tutorID);

                    System.out.println("Student ID: ");
                    String studentID = sc.nextLine();
                    preparedStatement.setString(2, studentID);

                    resultSet = preparedStatement.executeQuery();
                    printResultSetFromSession(resultSet);

                    break;

                case 2:
                    sql = "CALL TutorManager_update_Status (?, ?)";
                    preparedStatement = conn.prepareStatement(sql);

                    System.out.println("Assignment: ");
                    String assignment = sc.nextLine();
                    preparedStatement.setString(1, assignment);

                    System.out.println("Status: ");
                    String status = sc.nextLine();
                    preparedStatement.setString(2, status);

                    preparedStatement.executeQuery();
                    break;

                case 3:
                    sql = "CALL TutorManager_add_courses (?, ?)";
                    preparedStatement = conn.prepareStatement(sql);

                    System.out.println("TutorID: ");
                    int tutorID2 = Integer.valueOf(sc.nextLine());
                    preparedStatement.setInt(1, tutorID2);

                    System.out.println("CourseID: ");
                    int courseID = Integer.valueOf(sc.nextLine());
                    preparedStatement.setInt(2, courseID);

                    preparedStatement.execute();
                    break;

                case 4:
                    sql = "CALL TutorManager_num_tutors_of_courseCode (?)";
                    preparedStatement = conn.prepareStatement(sql);

                    System.out.println("CourseID: ");
                    String courseCode = sc.nextLine();
                    preparedStatement.setString(1, courseCode);

                    resultSet = preparedStatement.executeQuery();
                    printResultSetFromTutorCourse(resultSet);
                    break;

                case 5:
                    sql = "CALL Archive_session (?)";
                    preparedStatement = conn.prepareStatement(sql);

                    System.out.println("Delete all sessions before this date: ");
                    String startTime = sc.nextLine();
                    preparedStatement.setString(1, startTime);

                    preparedStatement.execute();
                    break;

                default:
                    break outerloop;

            }



        }

    }
}
