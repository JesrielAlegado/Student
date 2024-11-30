package studentviolation2e; 

import java.util.InputMismatchException;
import java.util.Scanner;

public class Complainant {
    Scanner sc = new Scanner(System.in);
    config conf = new config();

    public void comtransac() {
        int action;
        do {
            try {
                System.out.println("\n-------------------------------------------------");
                System.out.println("                == COMPLAINANT ==");
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
                        addComplainant();
                        break;

                    case 2:
                        viewComplainant();
                        break;

                    case 3:
                        viewComplainant();
                        updateComplainant();
                        break;

                    case 4:
                        viewComplainant();
                        deleteComplainant();
                        viewComplainant();
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



    public void addComplainant() {
        String name;
        while (true) {
            System.out.print("Enter Complainant Name: ");
            name = sc.nextLine();
            if (name.trim().isEmpty()) {
                System.out.println("Name cannot be empty. Please enter a valid name.\n");
            } else {
                break;
            }
        }

        String prog;
        while (true) {
            System.out.print("Enter Program: ");
            prog = sc.nextLine();
            if (prog.trim().isEmpty()) {
                System.out.println("Program cannot be empty. Please enter a valid program.\n");
            } else {
                break;
            }
        }

        String pnum;
        while (true) {
            System.out.print("Enter Phone Number: ");
            pnum = sc.nextLine();

            if (isValidPhoneNumber(pnum)) {
                break;
            } else {
                System.out.println("Invalid phone number format. Please enter digits only.\n");
            }
        }

        String sql = "INSERT INTO COMPLAINANT (c_name, program, contact) VALUES (?, ?, ?)";
        conf.addRecord(sql, name, prog, pnum);
    }

    public void viewComplainant() {
        System.out.println("\n===================================== COMPLAINANT LIST ======================================");
        String qry = "SELECT * FROM COMPLAINANT";
        String[] header = {"ID", "Name", "Program", "Contact Number"};
        String[] column = {"c_id", "c_name", "program", "contact"};
        conf.viewRecords(qry, header, column);
    }

    private void updateComplainant() {
        System.out.print("Enter ID to Update: ");
        int cid = sc.nextInt();
        sc.nextLine();

        while(conf.getSingleValue("SELECT c_id FROM COMPLAINANT WHERE c_id = ?", cid) == 0) {
            System.out.println("Selected ID doesn't exist! ");
            System.out.print("Select Staff ID Again: ");
            cid = sc.nextInt();
            sc.nextLine();
        }

        String name;
        while (true) {
            System.out.print("Enter Complainant Name: ");
            name = sc.nextLine();
            if (name.trim().isEmpty()) {
                System.out.println("Name cannot be empty. Please enter a valid name.\n");
            } else {
                break;
            }
        }

        String prog;
        while (true) {
            System.out.print("Enter Program: ");
            prog = sc.nextLine();
            if (prog.trim().isEmpty()) {
                System.out.println("Program cannot be empty. Please enter a valid program.\n");
            } else {
                break;
            }
        }

        String pnum;
        while (true) {
            System.out.print("Enter Phone Number: ");
            pnum = sc.nextLine();

            if (isValidPhoneNumber(pnum)) {
                break;
            } else {
                System.out.println("Invalid phone number format. Please enter digits only.\n");
            }
        }

        String qry = "UPDATE COMPLAINANT SET c_name = ?, program = ?, contact = ? WHERE c_id = ?";
        conf.updateRecord(qry, name, prog, pnum, cid);
    }

    private void deleteComplainant() {
        System.out.print("Enter ID to Delete: ");
        int cid = sc.nextInt();
        sc.nextLine();

        while(conf.getSingleValue("SELECT c_id FROM COMPLAINANT WHERE c_id = ?", cid) == 0) {
            System.out.println("Selected ID doesn't exist! ");
            System.out.print("Select Staff ID Again: ");
            cid = sc.nextInt();
            sc.nextLine();
        }

        String qry = "DELETE FROM COMPLAINANT WHERE c_id = ?";
        conf.deleteRecord(qry, cid);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d+");
    }
}
