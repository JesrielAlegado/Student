package studentviolation2e; 

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Violation_Record {
    Scanner sc = new Scanner(System.in);
    config conf = new config();

    private static final String[] VALID_STATUSES = {"Pending", "Resolved", "In Progress"};

    public void viorec() {
        int action;
        do {
            try {
                System.out.println("\n-------------------------------------------------");
                System.out.println("              == VIOLATION RECORD ==");
                System.out.println("-------------------------------------------------");   
                
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
                        addVioRec();
                        break;
                        
                    case 2:
                        viewVioRec();
                        break;
                        
                    case 3:
                        viewVioRec();
                        updateVioRec();
                        break;
                        
                    case 4:
                        viewVioRec();
                        deleteVioRec();
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

    public void addVioRec() {
        Students sts = new Students();
        sts.viewStudent();

        int sid = getValidId("SELECT s_id FROM STUDENT WHERE s_id = ?", "Student");

        Complainant com = new Complainant();
        com.viewComplainant();

        int cid = getValidId("SELECT c_id FROM COMPLAINANT WHERE c_id = ?", "Complainant");

        Violation vio = new Violation();
        vio.viewViolation();
        
        int vid = getValidId("SELECT v_id FROM VIOLATION WHERE v_id = ?", "Violation");
        
        String action;
        while (true) {
            System.out.print("Enter Action Taken: ");
            action = sc.nextLine();
            if (action.trim().isEmpty()) {
                System.out.println("Action taken cannot be empty. Please enter a valid action taken.\n");
            } else {
                break;
            }
        }
         
         LocalDate currdate = LocalDate.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String date = currdate.format(format);

        
        String status = getValidStatus();

        String vioqry = "INSERT INTO VIOLATION_RECORD (s_id, c_id, v_id, action_taken, date, status) VALUES (?, ?, ?, ?, ?, ?)";
        conf.addRecord(vioqry, sid, cid, vid, action, date, status);
    }

    public void viewVioRec() {
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("                                                                   == VIOLATION RECORDS LIST ==");
        String qry = "SELECT vr_id, s_name, c_name, vio_type, action_taken, date, status FROM VIOLATION_RECORD "
                + "LEFT JOIN STUDENT ON STUDENT.s_id = VIOLATION_RECORD.s_id "
                + "LEFT JOIN COMPLAINANT ON COMPLAINANT.c_id = VIOLATION_RECORD.c_id "
                + "LEFT JOIN VIOLATION ON VIOLATION.v_id = VIOLATION_RECORD.v_id";
        
        String[] header = {"VRID", "Student", "Complainant", "Violation", "Action Taken", "Date", "Status"};
        String[] column = {"vr_id", "s_name", "c_name", "vio_type", "action_taken", "date", "status"};
        
        conf.viewRecords(qry, header, column);
    }

    public void updateVioRec() {
        System.out.print("Enter Violation Record ID: ");
        int vrid = sc.nextInt();
        sc.nextLine();

        while (conf.getSingleValue("SELECT vr_id FROM VIOLATION_RECORD WHERE vr_id = ?", vrid) == 0) {
            System.out.println("Selected ID doesn't exist! ");
            System.out.print("Select Violation Record ID Again: ");
            vrid = sc.nextInt();
            sc.nextLine();
        }
        
        Complainant com = new Complainant();
        com.viewComplainant();     
        int cid = getValidId("SELECT c_id FROM COMPLAINANT WHERE c_id = ?", "Complainant");
        
        Violation vio = new Violation();
        vio.viewViolation();
        int vid = getValidId("SELECT v_id FROM VIOLATION WHERE v_id = ?", "Violation");

        String action;
        while (true) {
            System.out.print("Enter Action Taken: ");
            action = sc.nextLine();
            if (action.trim().isEmpty()) {
                System.out.println("Action taken cannot be empty. Please enter a valid action taken.\n");
            } else {
                break;
            }
        }
         
         LocalDate currdate = LocalDate.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String date = currdate.format(format);

        String status = getValidStatus();

        String qry = "UPDATE VIOLATION_RECORD SET c_id = ?, v_id = ?, action_taken = ?, date = ?, status = ? WHERE vr_id = ?";
        conf.updateRecord(qry, cid, vid, action, date, status, vrid);
    }

    private void deleteVioRec() {
        System.out.print("Enter ID to Delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        while (conf.getSingleValue("SELECT vr_id FROM VIOLATION_RECORD WHERE vr_id = ?", id) == 0) {
            System.out.println("Selected ID doesn't exist! ");
            System.out.print("Select Violation Record ID Again: ");
            id = sc.nextInt();
            sc.nextLine();
        }

        System.out.print("Are you sure you want to delete this record? (Yes/No): ");
        String confirmation = sc.next();

        if (confirmation.equalsIgnoreCase("Yes")) {
            String qry = "DELETE FROM VIOLATION_RECORD WHERE vr_id = ?";
            conf.deleteRecord(qry, id);
            System.out.println("Violation record deleted successfully.");
        } else {
            System.out.println("Deletion canceled.");
        }
    }
    
    private int getValidId(String sqlQuery, String idType) {
        int id = 0;
        while (true) {
            System.out.print("Enter " + idType + " ID: ");
            id = sc.nextInt();
            sc.nextLine();
            if (conf.getSingleValue(sqlQuery, id) != 0) {
                break;
            } else {
                System.out.println("Selected " + idType + " ID doesn't exist! Please try again.");
            }
        }
        return id;
    }

    private String getValidStatus() {
        String status;
        while (true) {
            System.out.print("Enter Status (Pending, Resolved, In Progress): ");
            status = sc.nextLine().trim();
            boolean valid = false;
            for (String validStatus : VALID_STATUSES) {
                if (status.equalsIgnoreCase(validStatus)) {
                    valid = true;
                    break;
                }
            }
            if (valid) {
                break;
            } else {
                System.out.println("Invalid status. Please enter a valid status.");
            }
        }
        return status;
    }
}
