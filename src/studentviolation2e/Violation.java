package studentviolation2e;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Violation {
    Scanner sc = new Scanner(System.in);
    config conf = new config();

    public void VTransactions() {
        int act;

        do {
            try {
                System.out.println("1. Add Violation");
                System.out.println("2. View Violation");
                System.out.println("3. Update Violation");
                System.out.println("4. Delete Violation");
                System.out.println("5. Back to Main Menu");

                System.out.print("\nPlease Choose an Option: ");
                act = sc.nextInt();
                sc.nextLine();  // Consume newline after nextInt()

                switch (act) {
                    case 1:
                        addViolation();
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
                        System.out.println("Returning to the main menu...");
                        break;

                    default:
                        System.out.println("Invalid Option.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine(); // Clear the buffer
                act = -1;  // Reset the action to -1 to allow retry
            }
        } while (act != 5);  // Repeat until the user selects "5"
    }

    private void addViolation() {
        Student stud = new Student();
        stud.viewStudent(); // Display students before selecting

        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();

        String csql = "SELECT ID FROM s_sv WHERE ID = ?";
        while (conf.getSingleValue(csql, id) == 0) {
            System.out.println("Selected ID doesn't exist! ");
            System.out.print("Select Student Id Again: ");
            id = sc.nextInt();
            sc.nextLine();  // Consume newline after nextInt()
        }

        sc.nextLine();  // Clear buffer before reading strings

        System.out.print("Enter Teacher: ");
        String teach = sc.nextLine();

        System.out.print("Enter Reported by: ");
        String reported = sc.nextLine();

        System.out.print("Enter Description: ");
        String desc = sc.nextLine();

        System.out.print("Enter Consequences: ");
        String con = sc.nextLine();

        String status = "Pending";

        // SQL query to insert violation record
        String bookqry = "INSERT INTO violation (v_id, ID, teacher, reportedby, description, consequences, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        conf.addRecord(bookqry, id, teach, reported, desc, con, status);
    }

    private void viewViolation() {
        String qry = "SELECT v_id, s_fname, s_lname, teacher, reportedby, description, consequences, status FROM violation "
                + "LEFT JOIN s_sv ON s_sv.ID = violation.ID";

        String[] header = {"VID", "Student First Name", "Student Last Name", "Teacher", "Reportedby", "Description", "Consequences", "Status"};
        String[] column = {"v_id", "s_fname", "s_lname", "teacher", "reportedby", "description", "consequences", "status"};

        conf.viewRecords(qry, header, column);
    }

    private void updateViolation() {
        System.out.print("Enter Violation ID to Update: ");
        int v_id = sc.nextInt();

        // Validate if the violation ID exists
        while (conf.getSingleValue("SELECT v_id FROM violation WHERE v_id = ?", v_id) == 0) {
            System.out.println("Selected Violation ID doesn't exist! ");
            System.out.print("Select Violation Id Again: ");
            v_id = sc.nextInt();
            sc.nextLine();  // Consume newline after nextInt()
        }

        sc.nextLine();  // Clear buffer before reading strings

        System.out.print("Enter Student ID: ");
        int sid = sc.nextInt();

        // Validate if the student ID exists
        String csql = "SELECT ID FROM s_sv WHERE ID = ?";
        while (conf.getSingleValue(csql, sid) == 0) {
            System.out.println("Selected Student ID doesn't exist! ");
            System.out.print("Select Student Id Again: ");
            sid = sc.nextInt();
            sc.nextLine();  // Consume newline after nextInt()
        }

        sc.nextLine();  // Clear buffer before reading strings

        System.out.print("Enter Teacher: ");
        String teach = sc.nextLine();

        System.out.print("Enter Reported by: ");
        String reported = sc.nextLine();

        System.out.print("Enter Description: ");
        String desc = sc.nextLine();

        System.out.print("Enter Consequences: ");
        String con = sc.nextLine();

        // Update violation record in the database
        String qry = "UPDATE violation SET ID = ?, teacher = ?, reportedby = ?, description = ?, consequences = ?, status = ? WHERE v_id = ?";
        conf.updateRecord(qry, sid, teach, reported, desc, con, "Pending", v_id);
    }

    private void deleteViolation() {
        System.out.print("Enter Violation ID to Delete: ");
        int v_id = sc.nextInt();

        // Validate if the violation ID exists
        while (conf.getSingleValue("SELECT v_id FROM violation WHERE v_id = ?", v_id) == 0) {
            System.out.println("Selected Violation ID doesn't exist! ");
            System.out.print("Select Violation Id Again: ");
            v_id = sc.nextInt();
            sc.nextLine();  // Consume newline after nextInt()
        }

        // SQL query to delete violation record
        String qry = "DELETE FROM violation WHERE v_id = ?";
        conf.deleteRecord(qry, v_id);
    }
}
