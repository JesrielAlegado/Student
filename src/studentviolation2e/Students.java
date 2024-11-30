package studentviolation2e;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Students {
    Scanner sc = new Scanner(System.in);
    config conf = new config();

    public void sTransaction(){
        int action;
        do {
            try {
            System.out.println("\n-------------------------------------------------");
            System.out.println("                  == STUDENT ==");
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
                    addStudent();
                    viewStudent();
                    break;
                    
                case 2:
                    viewStudent();
                    break;
                    
                case 3:
                    viewStudent();
                    updateStudent();
                    viewStudent();
                    break;
                    
                case 4:
                    viewStudent();
                    deleteStudent();
                    viewStudent();
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
   
    
    public void addStudent(){
            String name;
                while (true) {
                    System.out.print("Enter Student Name: ");
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
                    if (name.trim().isEmpty()) {
                        System.out.println("Name cannot be empty. Please enter a valid name.\n");
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

                String sql = "INSERT INTO STUDENT (s_name, program, pnum) VALUES (?, ?, ?)";   
                conf.addRecord(sql, name, prog, pnum);
                
        }
    
     public void viewStudent(){
        System.out.println("");
        System.out.println("======================================= STUDENT LIST ========================================");
            String qry = "SELECT * FROM STUDENT";
            String[] header = {"ID", "Name", "Pogram", "Contact Number"};
            String[] column = {"s_id", "s_name", "program", "pnum"};
            
        conf.viewRecords(qry, header, column);
    
    }
    
     
    private void updateStudent(){
        System.out.print("Enter ID to Update: ");
        int sid = sc.nextInt();
        sc.nextLine();
        
            while(conf.getSingleValue("SELECT s_id FROM STUDENT WHERE s_id = ?",sid)==0){
                System.out.println("Selected ID doesn't exist! ");
                System.out.print("Select Student Id Again: ");
                sid = sc.nextInt();
                sc.nextLine();
        }
            
            String name;
                while (true) {
                    System.out.print("Enter Student Name: ");
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
                    if (name.trim().isEmpty()) {
                        System.out.println("Name cannot be empty. Please enter a valid name.\n");
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
                
         String qry = "UPDATE STUDENT SET s_name = ?, program = ?, pnum = ? WHERE s_id = ?";
         conf.updateRecord(qry, name, prog, pnum, sid);
    
    }
    
    
    private void deleteStudent(){
        System.out.print("Enter ID to Delete: ");
        int sid = sc.nextInt();
        sc.nextLine();
        
            while(conf.getSingleValue("SELECT s_id FROM STUDENT WHERE s_id = ?",sid)==0){
                System.out.println("Selected ID doesn't exist! ");
                System.out.print("Select Student Id Again: ");
                sid = sc.nextInt();
                sc.nextLine();
        }
        
                String qry = "DELETE FROM STUDENT WHERE s_id = ?";
                conf.deleteRecord(qry, sid);

}
    
       private boolean isValidPhoneNumber(String phoneNumber) {
            return phoneNumber.matches("\\d+"); 
        }
    
       
}
