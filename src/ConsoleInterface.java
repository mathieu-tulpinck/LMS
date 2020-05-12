import java.util.*;

public class ConsoleInterface {

    public static void main(String[] args) {


        Library lib = new Library();// to be replaced by singleton syntax

        //authentication module to be added

        int choice = 0;
        Scanner console = new Scanner(System.in);

        System.out.println("Welcome to the LMS.");

        while(!(choice == 3)) {

            System.out.println("Following options are available");
            System.out.println("1. Add member");
            System.out.println("2. Modify details of a member");
            System.out.println("3. Exit");

            choice = takeInput(console);

            switch(choice) {
                case 1:
                    addMember(lib, console);
                    break;

                case 2:
                    updateMember(lib, console);
                    break;

                default:
                    break;
            }
        }
    }

    public static int takeInput(Scanner console) {// safe input method to be designed
        System.out.println("Enter choice: ");
        int choice = console.nextInt();
        return choice;
    }

    public static void addMember(Library lib, Scanner console) {
        Member member = lib.createMember(console);
        int memberID = lib.addMember(member);
        member.setMemberID(memberID);

        if(memberID > 0) {
            System.out.println("Member created with following details:\n" + member );
        } else {
            System.out.println("Process failed.");
        }
    }

    public static void updateMember(Library lib,  Scanner console) {
        int newPhone = 0;
        String newAddress = "";

        System.out.println("Provide the Member ID:");
        int memberID = console.nextInt();
        System.out.println("Provide new address or phone:");
        if(console.hasNextInt()) {
            newPhone = console.nextInt();
        } else {
            newAddress = console.next();
        }
        if(lib.updateMember(memberID, newAddress, newPhone) != 0)
            System.out.println("Details of member " + memberID + " updated." );
    }
}