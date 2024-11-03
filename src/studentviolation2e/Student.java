package studentviolation2e;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Student {
    Scanner sc = new Scanner(System.in);
    config conf = new config();

    public void sTransactions(){
        int action;
        do {
            System.out.println("1. ADD");
            System.out.println("2. VIEW");
            System.out.println("3. UPDATE");
            System.out.println("4. DELETE");
            System.out.println("5. EXIT");

            System.out.print("\nENTER action: ");
            action = sc.nextInt();
            sc.nextLine();

            switch(action) {
                case 1:
                    addStudentviolation();
                    break;
                    
                case 2:
                    viewStudentviolation();
                    break;
                    
                case 3:
                    viewStudentviolation();
                    updateStudentviolation();
                    viewStudentviolation();
                    break;
                    
                case 4:
                    viewStudentviolation();
                    deleteStudentViolation();
                    viewStudentviolation();
                    break;
                    
                case 5:
                    System.out.println("\nExiting...");
                    sc.close();  
                    break;
                    
                default:
                    System.out.println("Invalid option.");
            }
        } while(action != 5);
    }
    
    public void addStudentviolation() {
        String fname = promptValidFirstName();
        String lname = promptValidLastName();
        String program = promptValidProgram();
        String violation = promptValidViolation();
        String date = promptValidDate();
        
        String sql = "INSERT INTO s_sv (s_fname, s_lname, Program, Violation, Date) VALUES (?, ?, ?, ?, ?)";
        
        conf.addRecord(sql, fname, lname, program, violation, date);
        System.out.println("Record added successfully.\n");
    }
    
    public void viewStudentviolation() {
        System.out.println("");
        String qry= "SELECT * FROM s_sv";
        String[] hrds = {"ID", "First Name", "Last Name", "Program", "Violation", "Date"};
        String[] clmns = {"ID", "s_fname", "s_lname", "Program", "Violation", "Date"};
        
        conf.viewRecords(qry, hrds, clmns);
        System.out.println("");
   }   
  
    public void updateStudentviolation() {
        System.out.print("Enter ID to Update: ");
        int id = sc.nextInt();
        sc.nextLine();

        while(conf.getSingleValue("SELECT ID FROM s_sv WHERE ID = ?", id) == 0) {
            System.out.println("Selected ID doesn't exist! ");
            System.out.print("Select student ID Again: ");
            id = sc.nextInt();
            sc.nextLine();
        }
        
        String fname = promptValidFirstName();
        String lname = promptValidLastName();
        String program = promptValidProgram();
        String violation = promptValidViolation();
        String date = promptValidDate();
        
        String sqlUpdate =  "UPDATE s_sv SET s_fname = ?, s_lname = ?, Program = ?, Violation = ?, Date = ? WHERE ID = ?";
        
        conf.updateRecord(sqlUpdate, fname, lname, program, violation, date, id);
        System.out.println("Record updated successfully.\n");
    }
        
    public void deleteStudentViolation() {
        System.out.print("Enter ID to Delete: ");
        int id = sc.nextInt();
        sc.nextLine(); 

        while(conf.getSingleValue("SELECT ID FROM s_sv WHERE ID = ?", id) == 0) {
            System.out.println("Selected ID doesn't exist! ");
            System.out.print("Select student ID Again: ");
            id = sc.nextInt();
            sc.nextLine();
        }

        String qry = "DELETE FROM s_sv WHERE ID = ?";
        conf.deleteRecord(qry, id);
        System.out.println("Record deleted successfully.");
    }


    private String promptValidFirstName() {
        String fname;
        do {
            System.out.print("Enter New First Name (only alphabets): ");
            fname = sc.nextLine();
        } while (!isValidName(fname));
        return fname;
    }

    private String promptValidLastName() {
        String lname;
        do {
            System.out.print("Enter New Last Name (only alphabets): ");
            lname = sc.nextLine();
        } while (!isValidName(lname));
        return lname;
    }

    private String promptValidProgram() {
        String program;
        do {
            System.out.print("Enter New Program (only alphabets): ");
            program = sc.nextLine();
        } while (!isValidName(program));
        return program;
    }

    private String promptValidViolation() {
        String violation;
        do {
            System.out.print("Enter New Violation (must not be empty): ");
            violation = sc.nextLine();
        } while (violation.isEmpty());
        return violation;
    }

    private String promptValidDate() {
        String date;
        do {
            System.out.print("Enter New Date (format YYYY-MM-DD): ");
            date = sc.nextLine();
        } while (!isValidDate(date));
        return date;
    }

    private boolean isValidName(String name) {
        return Pattern.matches("^[A-Za-z]+$", name);
    }

    private boolean isValidDate(String date) {
        return Pattern.matches("^\\d{4}-\\d{2}-\\d{2}$", date);
    }
}
