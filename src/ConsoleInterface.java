import java.util.*;

public class ConsoleInterface {

    public static final int LOAN_DURATION = 30;

    public static void main(String[] args) {

        LibraryDAO lib = LibraryDAO.getInstance();

        //authentication module to be added

        int choice = 0;
        Scanner console = new Scanner(System.in);

        System.out.println("Welcome to the LMS.");

        while (!(choice == 3)) {

            System.out.println("Following options are available");
            System.out.println("1. Add member");
            System.out.println("2. Modify details of a member");
            System.out.println("3. Add books");
            System.out.println("4. Issue books");
            System.out.println("5. Exit");

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

                case 4:
                    issueBook(lib, console);
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
        Member emptyMember = new Member();
        Member member = emptyMember.createMember(console);
        System.out.println("Can the member benefit from a reduction?");
        String choice = console.next();
        if(choice.equals("yes")) {
            member.chooseMembershipType(console, member);
        } // else member will keep default normal MembershipType
        int memberID = lib.addMember(member);
        member.setMemberID(memberID);

        if (memberID != 0) {
            System.out.println("Member created with following details:\n" + member);
        } else {
            System.out.println("Process failed.");
        }
    }

    public static void updateMember(LibraryDAO lib, Scanner console) {
        int newPhone = 0;
        String newAddress = "";

        System.out.println("Provide Member ID:");
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
        Book book = new Book();
        boolean stop = false;
        ArrayList<Book> bookBatch = new ArrayList<Book>();
        ArrayList<Integer> bookIDs = new ArrayList<Integer>();
        do {
            book = book.createBook(console);
            bookBatch.add(book);// only add if book != null

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

    public static void issueBook(LibraryDAO lib, Scanner console){// Limit number of simultaneous book loans
        Member borrower;
        Book book = null;
        ArrayList<Book> bookBatch = new ArrayList<>();
        GregorianCalendar dueDate = getDueDate();
        boolean stop = false;

        System.out.println("Provide Member ID: ");
        int memberID = console.nextInt();

        borrower = lib.searchMember(memberID);
        if(borrower == null) {
            System.out.println("Member does not exist");// createMember functionality // check membership validity
        }

        do {
            System.out.println("Enter Book ID to be lent out: ");
            int bookID = console.nextInt();

            book = lib.searchBook(bookID);// search on books
            if(book != null) {
                bookBatch.add(book);
            } else {
                System.out.println("Book does not exist");
            }

            System.out.println("Issue more loans?");
            String s = console.next();

            if (s.equals("no")) {
                stop = true;
            }
        } while (!stop);

        int affectedRecords = lib.issueBook(borrower.getMemberID(), bookBatch, dueDate);
        if(affectedRecords != 0) {
            System.out.println("Total loan records inserted in db: " + affectedRecords);

        } else {
            System.out.println("Process failed");
        }
    }

    public static GregorianCalendar getDueDate() {
        GregorianCalendar dueDate = new GregorianCalendar();
        dueDate.add(GregorianCalendar.DATE, LOAN_DURATION);

        return dueDate;
    }
}



