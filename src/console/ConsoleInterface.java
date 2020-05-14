package console;
import db.*;
import library.*;

import java.util.*;

public class ConsoleInterface {
    public static String userNameLibrarian;
    public static boolean userLoggedin = false;

    public static void main(String[] args) {

        LibraryDAO lib = new LibraryDAO();// to be replaced by singleton syntax
        LibrarianDAO librarianDAO = new LibrarianDAO();
        MemberDAO memberDAO = new MemberDAO();

        int choice = 0;
        Scanner console = new Scanner(System.in);

        //Login module
        login(librarianDAO, console);

        System.out.println("Acces granted, welcome to the Library Management System!");

        //interface loop
        do {
            System.out.println("Please make a choice between the following options (1 - 9):");
            System.out.println("1. Add member");
            System.out.println("2. Modify details of a member");
            System.out.println("3. Add books");

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

                case 7:
                    extendMembership(memberDAO, console);
                    break;

                case 8:
                    userLoggedin = false; //user log out
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


    public static void login(LibrarianDAO librarianDAO, Scanner console) {
        do {
            System.out.println("Please enter your username and password");
            System.out.println("Username : ");
            String userName = console.next();
            System.out.println("Password : ");
            String password = console.next();

            userLoggedin = librarianDAO.verifyUserPassword(userName, password); //pass username and password to LibrarianDAO

            if (userLoggedin) {
                userNameLibrarian = userName;
            }
        } while (!userLoggedin);
    }

    public static void extendMembership(MemberDAO memberDAO, Scanner console) {
        System.out.println("Memberid : ");
        int memberId = console.nextInt();
        Membership membership = memberDAO.getMembership(memberId);//Pass MemberID to memberDAO
        memberDAO.extendMembershipYear(membership.getMembershipID());
        System.out.println("Membership extended for 1 year, member has to pay :" + membership.getPrice()+" Euro"); //Invoicing could be better
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
                System.out.println("Book created with following details:\n" + bookBatch.get(i));
            }
        } else {
            System.out.println("Process failed");
        }
    }
}