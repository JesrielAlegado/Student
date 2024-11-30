package studentviolation2e;

import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentViolation2E {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        Students st = new Students();
        Complainant com = new Complainant();
        Violation vio = new Violation();
        Violation_Record viorec = new Violation_Record();
        Report rep = new Report();
        int op = -1; 
        
        do {
            try {
                System.out.println("\n------------------------------------------------------------");
                System.out.println("       ==== WELCOME TO STUDENT VIOLATION SYSTEM ====     ");
                System.out.println("------------------------------------------------------------");
   
                System.out.println("1. STUDENTS ");
                System.out.println("2. COMPLAINANT");
                System.out.println("3. VIOLATION ");
                System.out.println("4. VIOLATION RECORD ");
                System.out.println("5. REPORT ");
                System.out.println("6. EXIT");
                
                System.out.print("\nSelect an Option: ");
                op = sc.nextInt();
                sc.nextLine();

                switch (op) {
                    case 1:
                        st.sTransaction();
                        break;
                    
                    case 2:
                        com.comtransac();
                        break;
                        
                    case 3:
                        vio.viotransac();
                        break;

                    case 4:
                        viorec.viorec();
                        break;

                    case 5:
                        rep.ReportTransaction();
                        break;
                        
                    case 6:
                        System.out.println("Exiting....");
                        break;

                    default:
                        System.out.println("Invalid Option.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine();
                op = -1;
            }
        } while (op != 6);

        sc.close();
    }
    
}
