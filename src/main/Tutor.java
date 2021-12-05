package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import static main.JDBC.printResultSetFromSession;
import static main.JDBC.printResultSetFromTutor;

public class Tutor {

    private static Connection conn = null;
    private static Statement statement = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    private static int tutorID = -1;
    private static String firstName = "";
    private static String lastName = "";
    private static String email ="";
    private static ArrayList<String> languages = new ArrayList<>();
    private static ArrayList<String> courses = new ArrayList<>();


    public static void promptTutor(Connection conn, Scanner sc) throws SQLException {

        boolean loggedIn = false;
        System.out.println("Input what you would like to do: ");
//        outerloop:
        while (true) {

            if (loggedIn) {
                printTutorProfile();
                System.out.println("Choose options");
                System.out.println("1. view my working schedule"); // use search_appointments for this, which lets you search appointments by
                System.out.println("2. view appointment");// use tutor_list_appointments for getting every incomplete appointments.
                System.out.println("3. view working hours");
                System.out.println("4. log out");

                int instructionType = Integer.valueOf(sc.nextLine());
                String sql = "";
                statement = conn.createStatement();
                switch (instructionType) {
                    case 1:
                        sql = "CALL search_appointments(? , ? , ? )";
                        preparedStatement = conn.prepareStatement(sql);



                        String day1 = "2021-11-19 11:00:00";
                        String day2= "2021-11-19 11:00:00";
                        preparedStatement.setInt(1, tutorID);
                        preparedStatement.setString(2,day1 );
                        preparedStatement.setString(3, day2);
                        resultSet = preparedStatement.executeQuery();

                        while (resultSet.next()){
//                            System.out.println("working schedule for " + day1 +"~"+ day2);
                            int sessionID = resultSet.getInt("sessionID");
                            System.out.println("session: " +  sessionID);

                            String time =  resultSet.getString("startTime");
                            System.out.println("Start time: " +  time);

                            String duration =  resultSet.getString("duration");
                            System.out.println("duration: " +  duration);
                        }


                        break;
                    case 2:

                        sql = "CALL tutor_list_appointment_summary(?)";
                        preparedStatement = conn.prepareStatement(sql);


                        preparedStatement.setInt(1, tutorID);
                        resultSet = preparedStatement.executeQuery();
                        int sessionID = -1;
                        int duration =-1;
                        System.out.println("<Appointment List>");
                        while (resultSet.next()){
                            sessionID = resultSet.getInt("sessionID");
                            String tfirst = resultSet.getString("tfirst");
                            String tlast = resultSet.getString("tlast");
                            String sfirst = resultSet.getString("sfirst");
                            String slast = resultSet.getString("slast");
                            duration = resultSet.getInt("duration");
                            String startTime = resultSet.getString("startTime");

                            System.out.println( "ID : " + sessionID + "\n" +
                                                "tutor: " + tfirst +" " + tlast + "\n" +
                                                "student: " + sfirst +" " + slast + "\n" +
                                                "time: " + startTime + "\n" +
                                                "duration" + duration + "\n"
                            );
                        }

                        break;

                    case 3:

                        sql = "CALL total_hours_worked(?)";
                        preparedStatement = conn.prepareStatement(sql);


                        preparedStatement.setInt(1, tutorID);
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()){
                            int n = resultSet.getInt("total");
                            System.out.println("hours : " + n);
                        }


                        break;
                    case 4:
                        loggedIn = false;
                        System.out.println("Logged out");
                        break;

                    default:
                        break;

                }


            } else {
                System.out.println("Input what you would like to do: ");
                System.out.println("1. log in");
                System.out.println("2. register");
                int instructionType = Integer.valueOf(sc.nextLine());
                String sql = "";
                statement = conn.createStatement();
                switch (instructionType) {
                    case 1:
                        sql = "CALL tutor_info(?)";
                        preparedStatement = conn.prepareStatement(sql);

                        System.out.print("tutor ID: ");
                        tutorID = Integer.valueOf(sc.nextLine());
                        preparedStatement.setInt(1, tutorID);
                        resultSet = preparedStatement.executeQuery();

                        while (resultSet.next()) {
                            tutorID = resultSet.getInt("tutorID");
                            firstName = resultSet.getString("firstName");
                            lastName = resultSet.getString("lastName");
                            email = resultSet.getString("email");
                        }

                        sql = "CALL tutor_info_languages(?)";
                        preparedStatement = conn.prepareStatement(sql);
                        preparedStatement.setInt(1, tutorID);
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            languages.add(resultSet.getString("language"));
                        }


                        sql = "CALL tutor_info_courses(?)";
                        preparedStatement = conn.prepareStatement(sql);
                        preparedStatement.setInt(1, tutorID);
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            courses.add(resultSet.getString("courseCode"));
                            System.out.println("one iteration!");
                        }

                        if(tutorID != -1){
                            loggedIn = true;
                            System.out.println("Log in Successful!");
                        }


                        break;
                    case 2:
                        break;
                    default:
                        System.out.println("Invalid output!");
                        break;
                }

            }

        }
//            System.out.println("Input what you would like to do: ");
//            System.out.println("1 = register, 2 = find tutor, 3 = request tutor, 4 = view upcoming sessions, ? = quit with invalid value");
//            int instructionType = Integer.valueOf(sc.nextLine());
//            String sql = "";
//            statement = conn.createStatement();
//
//            switch (instructionType) {
//
//                case 1:
//                    sql = "CALL Student_register (?, ?, ?, ?, ?)";
//                    preparedStatement = conn.prepareStatement(sql);
//
//                    System.out.println("First name: ");
//                    String firstName = sc.nextLine();
//                    preparedStatement.setString(1, firstName);
//
//                    System.out.println("Last name: ");
//                    String lastName = sc.nextLine();
//                    preparedStatement.setString(2, lastName);
//
//                    System.out.println("Email: ");
//                    String email = sc.nextLine();
//                    preparedStatement.setString(3, email);
//
//                    System.out.println("Grade: ");
//                    int grade = Integer.valueOf(sc.nextLine());
//                    preparedStatement.setInt(4, grade);
//
//                    System.out.println("Major: ");
//                    String major = sc.nextLine();
//                    preparedStatement.setString(5, major);
//
//                    preparedStatement.execute();
//                    break;
//
//                case 2:
//                    sql = "CALL Student_filter_tutors (?, ?)";
//                    preparedStatement = conn.prepareStatement(sql);
//
//                    System.out.println("Course code: ");
//                    String courseCode = sc.nextLine();
//                    preparedStatement.setString(1, courseCode);
//
//                    System.out.println("Language: ");
//                    String language = sc.nextLine();
//                    preparedStatement.setString(2, language);
//
//                    resultSet = preparedStatement.executeQuery();
//                    printResultSetFromTutor(resultSet);
//                    break;
//
//                case 3:
//                    sql = "CALL Student_request_tutor (?, ?, ?, ?, ?)";
//                    preparedStatement = conn.prepareStatement(sql);
//
//                    System.out.println("StudentID: ");
//                    int studentID = Integer.valueOf(sc.nextLine());
//                    preparedStatement.setInt(1, studentID);
//
//                    System.out.println("TutorID");
//                    int tutorID = Integer.valueOf(sc.nextLine());
//                    preparedStatement.setInt(2, tutorID);
//
//                    System.out.println("AssignmentID: ");
//                    int assignmentID = Integer.valueOf(sc.nextLine());
//                    preparedStatement.setInt(3, assignmentID);
//
//                    System.out.println("Duration: ");
//                    int duration = Integer.valueOf(sc.nextLine());
//                    preparedStatement.setInt(4, duration);
//
//                    System.out.println("StartTime: ");
//                    String startTime = sc.nextLine();
//                    preparedStatement.setString(5, startTime);
//
//                    preparedStatement.execute();
//                    break;
//
//                case 4:
//                    sql = "CALL Student_view_upcoming_sessions (?)";
//                    preparedStatement = conn.prepareStatement(sql);
//
//                    System.out.println("StudentID: ");
//                    int studentID2 = Integer.valueOf(sc.nextLine());
//                    preparedStatement.setInt(1, studentID2);
//
//                    resultSet = preparedStatement.executeQuery();
//                    printResultSetFromSession(resultSet);
//                    break;
//
////                default:
////                    break outerloop;
//
//            }





    }

    public static void printTutorProfile(){

        System.out.println("-------<Profile>-------");
        System.out.println("TutorID: " + tutorID + "\nName: " + firstName + " " + lastName + "\nEmail: " + email);


        System.out.print("Languages:");
        for (String language : languages){
            System.out.print(" " + language);
        }
        System.out.println();

        System.out.print("Courses:");
        for (String course : courses){
            System.out.print(" " + course);
        }
        System.out.println("\n------------------------");

    }

}
