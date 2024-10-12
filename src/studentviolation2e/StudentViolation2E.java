package studentviolation2e;
import java.util.Scanner;

public class StudentViolation2E {
    
    public static void addStudents(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        System.out.print("\nStudent FIRST Name : ");
        String fname = sc.nextLine();
        System.out.print("Student LAST Name: ");
        String lname = sc.nextLine();
        System.out.print("Student PROGRAM: ");
        String program = sc.nextLine();
        System.out.print("Student VIOLATION: ");
        String violation = sc.nextLine();
        System.out.print("Student DATE: ");
        String date = sc.nextLine();
        
        String sql = "INSERT INTO s_sv (s_fname, s_lname, Program, Violation, Date) VALUES (?, ?, ?, ?, ?)";
        
        conf.addRecord(sql, fname, lname, program, violation, date);
        System.out.println("");
    }
    
    public static void viewStudentviolation() {
        System.out.println("");
        String qry= "SELECT * FROM s_sv";
        String[] hrds = {"ID", "First Name", "Last Name", "Program", "Violation", "Date"};
        String[] clmns = {"ID", "s_fname", "s_lname", "Program", "Violation", "Date"};
        
        config conf = new config();
        conf.viewRecords(qry, hrds, clmns);
        System.out.println("");
   }   
  
    public static void updateStudentviolation(){
        Scanner sc = new Scanner(System.in);
        
        System.out.print("\nEnter ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        
        System.out.print("Enter New First Name: ");
        String fname = sc.nextLine();
        
        System.out.print("Enter New Last Name: ");
        String lname = sc.nextLine();
        
        System.out.print("Enter New Program: ");
        String program = sc.nextLine();
        
        System.out.print("Enter New Violation: ");
        String violation = sc.nextLine();
        
        System.out.print("Enter New Date: ");
        String date = sc.nextLine();
        
        String sqlUpdate =  "UPDATE s_sv SET s_fname = ?, s_lname = ?, Program = ?, Violation = ?, Date = ? WHERE ID = ?";
        
        config conf = new config();
        conf.updateRecord(sqlUpdate, fname, lname, program, violation, date, id);
        System.out.println("");
    }
        
    public static void deleteStudentViolation(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        System.out.print("\nEnter ID to delete: ");
        int id = sc.nextInt();
               
        String qry = "DELETE FROM s_sv WHERE ID = ?";
       
        conf.deleteRecord(qry, id);
        
        System.out.println("");
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int action;
        do {
            System.out.println("1. ADD");
            System.out.println("2. VIEW");
            System.out.println("3. UPDATE");
            System.out.println("4. DELETE");
            System.out.println("5. EXIT");

            System.out.print("\nENTER action: ");
            action = sc.nextInt();

            switch(action) {
                case 1:
                    addStudents();
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
        }while(action != 5);
    }
}

    
