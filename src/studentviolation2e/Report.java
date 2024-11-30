package studentviolation2e;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Report {
    private Scanner sc = new Scanner(System.in);
    config conf = new config();

    public void ReportTransaction() {
        int action;
        do {
            try {
                System.out.println("\n-------------------------------------------------");
                System.out.println("              == REPORT HISTORY ==");
                System.out.println("-------------------------------------------------");

                System.out.println("1. Generate Specific Student");
                System.out.println("2. Generate All Student Violation History");
                System.out.println("3. Back to Main Menu");

                System.out.print("\nPlease Choose an Option: ");
                action = sc.nextInt();
                sc.nextLine();

                switch (action) {
                    case 1:
                        displayStudentVioCounts();
                        break;
                    case 2:
                        Violation_Record vio = new Violation_Record();
                        vio.viewVioRec();
                        break;
                    case 3:
                        System.out.println("Returning to the main menu...");
                        break;
                    default:
                        System.out.println("Invalid Option.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine();
                action = -1;
            }
        } while (action != 3);
    }

        public void displayStudentVioCounts() {
            Students stud = new Students();
            stud.viewStudent();

            System.out.print("Enter Student ID: ");
            int sid = sc.nextInt();
            sc.nextLine();

            String csql = "SELECT COUNT(*) FROM STUDENT WHERE s_id = ?";
            while (conf.getSingleValue(csql, sid) == 0) {
                System.out.println("Selected ID doesn't exist! ");
                System.out.print("Select Student ID Again: ");
                sid = sc.nextInt();
                sc.nextLine();
            }

            String studentQuery = "SELECT s_id, s_name, program, pnum FROM STUDENT WHERE s_id = ?";
            String vrQuery = "SELECT VIOLATION_RECORD.vr_id, COMPLAINANT.c_name AS complainant, VIOLATION.vio_type AS violation, " +
                             "VIOLATION_RECORD.date, VIOLATION_RECORD.status " +
                             "FROM VIOLATION_RECORD " +
                             "JOIN COMPLAINANT ON VIOLATION_RECORD.c_id = COMPLAINANT.c_id " +
                             "JOIN VIOLATION ON VIOLATION_RECORD.v_id = VIOLATION.v_id " +
                             "WHERE VIOLATION_RECORD.s_id = ?";

            try (Connection conn = conf.getConnection();
                 PreparedStatement studentStmt = conn.prepareStatement(studentQuery);
                 PreparedStatement vrStmt = conn.prepareStatement(vrQuery)) {

                studentStmt.setInt(1, sid);
                try (ResultSet studentResult = studentStmt.executeQuery()) {
                    if (studentResult.next()) {
                        System.out.println("\n----------------------------------------------------------------------------------------------------------");
                        System.out.println("                                           == STUDENT DETAILS ==                                             ");
                        System.out.println("----------------------------------------------------------------------------------------------------------");

                        System.out.println("Student Name: " + studentResult.getString("s_name"));
                        System.out.println("Program: " + studentResult.getString("program"));
                        System.out.println("Phone Number: " + studentResult.getString("pnum"));
                        System.out.println();

                        System.out.println("==========================================================================================================");
                        System.out.println("|                                       STUDENT VIOLATION HISTORY                                        |");
                        System.out.println("==========================================================================================================");
                        System.out.printf("| %-15s | %-20s | %-20s | %-20s | %-15s |%n", "Vio_Record ID", "Complainant", "Violation", "Date", "Status");
                        System.out.println("----------------------------------------------------------------------------------------------------------");

                        vrStmt.setInt(1, sid);
                        try (ResultSet vrResult = vrStmt.executeQuery()) {

                            while (vrResult.next()) {
                                System.out.printf("| %-15d | %-20s | %-20s | %-20s | %-15s |%n",
                                        vrResult.getInt("vr_id"),
                                        vrResult.getString("complainant"),
                                        vrResult.getString("violation"),
                                        vrResult.getString("date"),
                                        vrResult.getString("status"));
                            }
                        }
                        System.out.println("----------------------------------------------------------------------------------------------------------");
                    } else {
                        System.out.println("Student not found.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("SQL Error: " + e.getMessage());
            }
        }
        
    }
