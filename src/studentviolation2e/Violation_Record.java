package studentviolation2e; 

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Violation_Record {
    Scanner sc = new Scanner(System.in);
    config conf = new config();

    private static final String[] VALID_STATUSES = {"Pending", "Resolved", "In Progress"};

    public void viorec() {
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
                        addVioRec();
                        break;
                        
                    case 2:
                        viewVioRec();
                        break;
                        
                    case 3:
                        viewVioRec();
                        updateVioRec();
                        viewVioRec();
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

        Staff st = new Staff();
        st.viewStaff();

        int stid = getValidId("SELECT st_id FROM STAFF WHERE st_id = ?", "Staff");

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
        
        String status = getValidStatus();

        String vioqry = "INSERT INTO VIOLATION_RECORD (s_id, st_id, v_id, action_taken, date_resolved, status) VALUES (?, ?, ?, ?, ?, ?)";
        conf.addRecord(vioqry, sid, stid, vid, action, date, status);
    }

    public void viewVioRec() {
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("                                                                   == VIOLATION RECORDS LIST ==");
        String qry = "SELECT vr_id, s_name, st_name, vio_type, action_taken, date_resolved, status FROM VIOLATION_RECORD "
                + "LEFT JOIN STUDENT ON STUDENT.s_id = VIOLATION_RECORD.s_id "
                + "LEFT JOIN STAFF ON STAFF.st_id = VIOLATION_RECORD.st_id "
                + "LEFT JOIN VIOLATION ON VIOLATION.v_id = VIOLATION_RECORD.v_id";
        
        String[] header = {"VRID", "Student", "Staff", "Violation", "Action Taken", "Date Resolved", "Status"};
        String[] column = {"vr_id", "s_name", "st_name", "vio_type", "action_taken", "date_resolved", "status"};
        
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

        int sid = getValidId("SELECT s_id FROM STUDENT WHERE s_id = ?", "Student");
        int stid = getValidId("SELECT st_id FROM STAFF WHERE st_id = ?", "Staff");
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
        
        String status = getValidStatus();

        String qry = "UPDATE VIOLATION_RECORD SET s_id = ?, st_id = ?, v_id = ?, action_taken = ?, date_resolved = ?, status = ? WHERE vr_id = ?";
        conf.updateRecord(qry, sid, stid, vid, action, date, status, vrid);
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
