import java.util.*;

public class ConsoleInterface {

    public static void main(String[] args) {


        Library lib = new Library();// to be replaced by singleton syntax

        //authentication module to be added

        int choice = 0;
        Scanner console = new Scanner(System.in);

        while(!(choice == 2)) {
            System.out.println("Welcome to the LMS.");
            System.out.println("Following options are available");
            System.out.println("1. Add member");
            System.out.println("2. Exit");

            choice = takeInput(console);

            if (choice == 1) {
                int memberID = 0;
                Member member = lib.createMember(console);
                memberID = lib.addMember(member);
                member.setMemberID(memberID);

                if(memberID > 0) {
                    System.out.println("Member created with following details:\n" + member );
                    break;
                } else {
                    System.out.println("Process failed.");
                }
            }

        }



    }

    public static int takeInput(Scanner console) {// safe input method to be designed
        System.out.println("Enter choice: ");
        int choice = console.nextInt();
        return choice;
    }
}