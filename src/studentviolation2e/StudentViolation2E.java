package studentviolation2e;

import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentViolation2E {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        Student st = new Student();
        Violation vio = new Violation();
        
        
        int op = -1; 
        
        do {
            try {
                System.out.println("       WELCOME TO STUDENT VIOLATION SYSTEM      ");
   

                System.out.println("1. STUDENTS ");
                System.out.println("2. VIOLATIONS");
                System.out.println("3. EXIT ");

                System.out.print("\nSelect an Option: ");
                op = sc.nextInt();
                sc.nextLine(); 

                switch (op) {
                    case 1:
                        st.sTransactions();
                        break;
                    
                    case 2:
                        vio.VTransactions();
                    
                    case 3:
                        System.out.println("Exiting....");
                        break;

                    default:
                        System.out.println("Invalid Option.");
                }

            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a valid number.");
                sc.nextLine();  
                op = -1;  
            }
        } while (op != 2);

        sc.close(); 
    }
    }
    