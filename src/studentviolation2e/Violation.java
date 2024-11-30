package studentviolation2e;


import java.util.InputMismatchException;
import java.util.Scanner;


public class Violation {
    Scanner sc = new Scanner(System.in);
    config conf = new config();

    public void viotransac() {
        int action;
     do {
        try {
            System.out.println("\n-------------------------------------------------");
            System.out.println("                 == VIOLATION ==");
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

        String sql = "INSERT INTO VIOLATION (vio_type, severity) VALUES (?, ?)";
        conf.addRecord(sql, viotype, sever);
    }

    public void viewViolation() {
        System.out.println("");
        System.out.println("=========================== VIOLATION LIST ===========================");
      
        String qry = "SELECT * FROM VIOLATION";
        String[] header = {"ID", "Violation Type", "Severity"};
        String[] column = {"v_id", "vio_type", "severity"};
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

        String qry = "UPDATE VIOLATION SET vio_type = ?, severity = ? WHERE v_id = ?";
        conf.updateRecord(qry, viotype, sever, vid);
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
