package studentviolation2e;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Violation {
    Scanner sc = new Scanner(System.in);
    config conf = new config();

    public void viotransac() {
        int action;
     do {
        try {
            System.out.println("1. ADD");
            System.out.println("2. VIEW");
            System.out.println("3. UPDATE");
            System.out.println("4. DELETE");
            System.out.println("5. BACK TO MAIN MENU");

            System.out.print("\nEnter action: ");
            action = sc.nextInt();
            sc.nextLine();

            switch(action) {
                case 1:
                    addViolation();
                    viewViolation();
                    break;

                case 2:
                    viewViolation();
                    break;

                case 3:
                    viewViolation();
                    updateViolation();
                    viewViolation();
                    break;

                case 4:
                    viewViolation();
                    deleteViolation();
                    viewViolation();
                    break;

                case 5:
                        System.out.println("Going Back to Main Menu...");
                        break;
                    
                    default:
                        System.out.println("Invalid option.");
                }
                
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine();
                action = -1;
            }
        } while(action != 5);
    }

    public void addViolation() {
        String viotype;
        while (true) {
            System.out.print("Enter Violation Type: ");
            viotype = sc.nextLine();
            if (viotype.trim().isEmpty()) {
                System.out.println("Violation type cannot be empty. Please enter a valid violation type.\n");
            } else {
                break;
            }
        }

        String date = "";
        while (true) {
            System.out.print("Enter Date (YYYY-MM-DD format): ");
            date = sc.nextLine();

            String dateRegex = "^\\d{4}-\\d{2}-\\d{2}$";
            Pattern pattern = Pattern.compile(dateRegex);
            Matcher matcher = pattern.matcher(date);

            if (!matcher.matches()) {
                System.out.println("Invalid input. Please enter a valid date in the format YYYY-MM-DD.\n");
                continue;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            try {
                sdf.parse(date);
                break;
            } catch (ParseException e) {
                System.out.println("Invalid date. Please enter a valid date in the format YYYY-MM-DD.\n");
            }
        }

        String sever;
        while (true) {
            System.out.print("Enter Severity: ");
            sever = sc.nextLine();
            if (sever.trim().isEmpty()) {
                System.out.println("Severity cannot be empty. Please enter a valid severity.\n");
            } else {
                break;
            }
        }

        String sql = "INSERT INTO VIOLATION (vio_type, date, severity) VALUES (?, ?, ?)";
        conf.addRecord(sql, viotype, date, sever);
    }

    public void viewViolation() {
        System.out.println("");
        System.out.println("====================================== VIOLATION LIST =======================================");
        String qry = "SELECT * FROM VIOLATION";
        String[] header = {"ID", "Violation Type", "Date", "Severity"};
        String[] column = {"v_id", "vio_type", "date", "severity"};
        conf.viewRecords(qry, header, column);
    }

    private void updateViolation() {
        System.out.print("Enter ID to Update: ");
        int vid = sc.nextInt();
        sc.nextLine();

        while(conf.getSingleValue("SELECT v_id FROM VIOLATION WHERE v_id = ?", vid) == 0) {
            System.out.println("Selected ID doesn't exist! ");
            System.out.print("Select Violation Id Again: ");
            vid = sc.nextInt();
            sc.nextLine();
        }

        String viotype;
        while (true) {
            System.out.print("Enter Violation Type: ");
            viotype = sc.nextLine();
            if (viotype.trim().isEmpty()) {
                System.out.println("Violation type cannot be empty. Please enter a valid violation type.\n");
            } else {
                break;
            }
        }

        String date = "";
        while (true) {
            System.out.print("Enter Date (YYYY-MM-DD format): ");
            date = sc.nextLine();

            String dateRegex = "^\\d{4}-\\d{2}-\\d{2}$";
            Pattern pattern = Pattern.compile(dateRegex);
            Matcher matcher = pattern.matcher(date);

            if (!matcher.matches()) {
                System.out.println("Invalid input. Please enter a valid date in the format YYYY-MM-DD.\n");
                continue;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            try {
                sdf.parse(date);
                break;
            } catch (ParseException e) {
                System.out.println("Invalid date. Please enter a valid date in the format YYYY-MM-DD.\n");
            }
        }

        String sever;
        while (true) {
            System.out.print("Enter Severity: ");
            sever = sc.nextLine();
            if (sever.trim().isEmpty()) {
                System.out.println("Severity cannot be empty. Please enter a valid severity.\n");
            } else {
                break;
            }
        }

        String qry = "UPDATE VIOLATION SET vio_type = ?, date = ?, severity = ? WHERE v_id = ?";
        conf.updateRecord(qry, viotype, date, sever, vid);
    }

    private void deleteViolation() {
        System.out.print("Enter ID to Delete: ");
        int vid = sc.nextInt();
        sc.nextLine();

        while(conf.getSingleValue("SELECT v_id FROM VIOLATION WHERE v_id = ?", vid) == 0) {
            System.out.println("Selected ID doesn't exist! ");
            System.out.print("Select Violation Id Again: ");
            vid = sc.nextInt();
            sc.nextLine();
        }

        String qry = "DELETE FROM VIOLATION WHERE v_id = ?";
        conf.deleteRecord(qry, vid);
    }
}
