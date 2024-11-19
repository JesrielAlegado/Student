package studentviolation2e; 

import java.util.InputMismatchException;
import java.util.Scanner;

public class Staff {
    Scanner sc = new Scanner(System.in);
    config conf = new config();

    public void stafftransac() {
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
                        addStaff();
                        viewStaff();
                        break;

                    case 2:
                        viewStaff();
                        break;

                    case 3:
                        viewStaff();
                        updateStaff();
                        viewStaff();
                        break;

                    case 4:
                        viewStaff();
                        deleteStaff();
                        viewStaff();
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

    public void addStaff() {
        String name;
        while (true) {
            System.out.print("Enter Staff Name: ");
            name = sc.nextLine();
            if (name.trim().isEmpty()) {
                System.out.println("Name cannot be empty. Please enter a valid name.\n");
            } else {
                break;
            }
        }

        String pos;
        while (true) {
            System.out.print("Enter Position: ");
            pos = sc.nextLine();
            if (pos.trim().isEmpty()) {
                System.out.println("Position cannot be empty. Please enter a valid position.\n");
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

        String sql = "INSERT INTO STAFF (st_name, position, contact) VALUES (?, ?, ?)";
        conf.addRecord(sql, name, pos, pnum);
    }

    public void viewStaff() {
        System.out.println("\n======================================== STAFF LIST =========================================");
        String qry = "SELECT * FROM STAFF";
        String[] header = {"ID", "Name", "Position", "Contact Number"};
        String[] column = {"st_id", "st_name", "position", "contact"};
        conf.viewRecords(qry, header, column);
    }

    private void updateStaff() {
        System.out.print("Enter ID to Update: ");
        int sid = sc.nextInt();
        sc.nextLine();

        while(conf.getSingleValue("SELECT st_id FROM STAFF WHERE st_id = ?", sid) == 0) {
            System.out.println("Selected ID doesn't exist! ");
            System.out.print("Select Staff ID Again: ");
            sid = sc.nextInt();
            sc.nextLine();
        }

        String name;
        while (true) {
            System.out.print("Enter Staff Name: ");
            name = sc.nextLine();
            if (name.trim().isEmpty()) {
                System.out.println("Name cannot be empty. Please enter a valid name.\n");
            } else {
                break;
            }
        }

        String pos;
        while (true) {
            System.out.print("Enter Position: ");
            pos = sc.nextLine();
            if (pos.trim().isEmpty()) {
                System.out.println("Position cannot be empty. Please enter a valid position.\n");
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

        String qry = "UPDATE STAFF SET st_name = ?, position = ?, contact = ? WHERE st_id = ?";
        conf.updateRecord(qry, name, pos, pnum, sid);
    }

    private void deleteStaff() {
        System.out.print("Enter ID to Delete: ");
        int sid = sc.nextInt();
        sc.nextLine();

        while(conf.getSingleValue("SELECT st_id FROM STAFF WHERE st_id = ?", sid) == 0) {
            System.out.println("Selected ID doesn't exist! ");
            System.out.print("Select Staff ID Again: ");
            sid = sc.nextInt();
            sc.nextLine();
        }

        String qry = "DELETE FROM STAFF WHERE st_id = ?";
        conf.deleteRecord(qry, sid);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d+");
    }
}
