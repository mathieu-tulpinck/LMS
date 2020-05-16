package console;
import db.*;
import library.*;

import java.util.*;

public class ConsoleInterface {

    public static String userNameLibrarian;
    public static boolean userLoggedin = false;
    public static final int LOAN_DURATION = 30;

    public static void main(String[] args) {

        LibraryDAO lib = new LibraryDAO();
        LibrarianDAO librarianDAO = new LibrarianDAO();
        MemberDAO memberDAO = new MemberDAO();

        int choice = 0;
        //create scanner for console inserts by librarian
        Scanner console = new Scanner(System.in);

        //Login module, verify username & password
        login(librarianDAO, console);

        System.out.println("Access granted, welcome to the Library Management System!");

        //interface options after login
        do {
            System.out.println("Please make a choice between the following options (1 - 9):");
            System.out.println("1. Add member");
            System.out.println("2. Modify details of a member");
            System.out.println("3. Add books");
            System.out.println("4. Issue books");

            System.out.println("7. Extend Membership");
            System.out.println("8. Logout");
            System.out.println("9. Exit");

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

                case 7:
                    extendMembership(memberDAO, console);
                    break;

                case 8:
                    userLoggedin = false; //user gets logged out
                    System.out.println(userNameLibrarian + " succesfully logged out");
                    login(librarianDAO, console);
                    break;

                case 9:
                    System.out.println("LMS shutting down...");
                    break;

                default:
                    break;
            }
        } while (choice != 9);
    }

    //login method
    public static void login(LibrarianDAO librarianDAO, Scanner console) {
        do {
            System.out.println("Please enter your username and password");
            System.out.println("Username : ");
            String userName = console.next();
            System.out.println("Password : ");
            String password = console.next();

            //pass username and password to LibrarianDAO
            userLoggedin = librarianDAO.verifyUserPassword(userName, password);

            if (userLoggedin) {
                userNameLibrarian = userName;
            }
        } while (!userLoggedin);
    }

    //take input method
    public static int takeInput(Scanner console) {// safe input method to be designed
        System.out.println("Enter choice: ");
        int choice = console.nextInt();
        return choice;
    }

    //case 1
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
            System.out.println("library.Member created with following details:\n" + member);
        } else {
            System.out.println("Process failed.");
        }
    }

    //case 2
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

    //case 3
    public static void addBook(LibraryDAO lib, Scanner console) {
        Book book = new Book();
        boolean stop = false;
        ArrayList<Book> bookBatch = new ArrayList<Book>();
        ArrayList<Integer> bookIDs = new ArrayList<Integer>();
        do {
            book = book.createBook(console);
            bookBatch.add(book);// only add if book != null

            System.out.println("Add more books? yes / no");
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
                System.out.println("library.Book created with following details:\n" + bookBatch.get(i));
            }
        } else {
            System.out.println("Process failed");
        }
    }

    //case 4
    public static void issueBook(LibraryDAO lib, Scanner console){// Limit number of simultaneous book loans
        Member borrower;
        Book book = null;
        ArrayList<Book> bookBatch = new ArrayList<>();
        GregorianCalendar dueDate = getDueDate();
        boolean stop = false;

        System.out.println("Provide library.Member ID: ");
        int memberID = console.nextInt();

        borrower = lib.searchMember(memberID);
        if(borrower == null) {
            System.out.println("library.Member does not exist");// createMember functionality // check membership validity
        }

        do {
            System.out.println("Enter library.Book ID to be lent out: ");
            int bookID = console.nextInt();

            book = lib.searchBook(bookID);// search on books
            if(book != null) {
                bookBatch.add(book);
            } else {
                System.out.println("library.Book does not exist");
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

    //initialize calendar for loans
    public static GregorianCalendar getDueDate() {
        GregorianCalendar dueDate = new GregorianCalendar();
        dueDate.add(GregorianCalendar.DATE, LOAN_DURATION);

        return dueDate;
    }

    //case 7
    public static void extendMembership(MemberDAO memberDAO, Scanner console) {
        System.out.println("Provide Member_ID where membership has to be extended with 1 year: ");
        int memberId = console.nextInt();
        Member member = memberDAO.getMember(memberId);//Pass MemberID to memberDAO.getMembership
        memberDAO.extendMembershipYear(memberId);
        int amount;
        switch (member.getMembershipType()){
            case JUNIOR:
                amount = 5;
                break;
            case SENIOR:
            case STUDENT:
                amount = 10;
                break;
            case NORMAL:
            default:
                amount = 15;
                break;
        }
        System.out.println("Membership extended for 1 year, member has to pay :" + amount + " Euro. Ask for ID or studentID if necessary!"); //Invoicing could be better
    }


    //case 8 restarts the login procedure
    //case 9 breaks out of the console interface loop and ends the program doing so
}