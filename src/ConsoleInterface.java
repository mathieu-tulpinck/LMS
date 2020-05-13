import java.util.*;

public class ConsoleInterface {

    public static void main(String[] args) {


        LibraryDAO lib = new LibraryDAO();// to be replaced by singleton syntax

        //authentication module to be added

        int choice = 0;
        Scanner console = new Scanner(System.in);

        System.out.println("Welcome to the LMS.");

        while (!(choice == 3)) {

            System.out.println("Following options are available");
            System.out.println("1. Add member");
            System.out.println("2. Modify details of a member");
            System.out.println("3. Add books");
            System.out.println("4. Exit");

            choice = takeInput(console);

            switch (choice) {
                case 1:
                    addMember(lib, console);
                    break;

                case 2:
                    updateMember(lib, console);
                    break;

                case 3:
                    addBook(lib, console);
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

    public static void addMember(LibraryDAO lib, Scanner console) {
        Member member = lib.createMember(console);
        Membership membership = lib.createMembership(console);
        int memberID = lib.addMember(member, membership);
        member.setMemberID(memberID);
        membership.setMemberID(memberID);

        if (memberID != 0) {
            System.out.println("Member created with following details:\n" + member);
        } else {
            System.out.println("Process failed.");
        }
    }

    public static void updateMember(LibraryDAO lib, Scanner console) {
        int newPhone = 0;
        String newAddress = "";

        System.out.println("Provide the Member ID:");
        int memberID = console.nextInt();
        System.out.println("Provide new address or phone:");
        if (console.hasNextInt()) {
            newPhone = console.nextInt();
        } else {
            newAddress = console.next();
        }
        if (lib.updateMember(memberID, newAddress, newPhone) != 0)
            System.out.println("Details of member " + memberID + " updated.");
    }

    public static void addBook(LibraryDAO lib, Scanner console) {
        Book book;
        boolean stop = false;
        ArrayList<Book> bookBatch = new ArrayList<Book>();
        ArrayList<Integer> bookIDs = new ArrayList<Integer>();
        do {

            book = lib.createBook(console);
            bookBatch.add(book);

            System.out.println("Add more books?");
            String s = console.next();

            if (s.equals("no")) {
                stop = true;
            }

        } while (!stop);

        bookIDs = lib.addBook(bookBatch);

        if (!bookIDs.isEmpty()) {
            for (int i = 0; i < bookBatch.size(); i++) {
                int bookID = bookIDs.get(i);
                bookBatch.get(i).setBook_ID(bookID);
                System.out.println("Book created with following details:\n" + bookBatch.get(i));
            }
        } else {
            System.out.println("Process failed");
        }
    }
}
