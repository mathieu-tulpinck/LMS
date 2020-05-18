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
        //login(librarianDAO, console); Commented out for testing

        System.out.println("Access granted, welcome to the Library Management System!");

        //interface options after login
        do {
            System.out.println("Please make a choice between the following options (1 - 9):");
            System.out.println("1. Add member");
            System.out.println("2. Modify details of a member");
            System.out.println("3. Add books");
            System.out.println("4. Issue books");
            System.out.println("5. Return books");

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

                case 5:
                    returnBook(lib, console);

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
        String userName, password;

        do {
            System.out.println("Please enter your username and password");
            System.out.println("Username : ");
            userName = console.next();
            System.out.println("Password : ");
            password = console.next();

            //pass username and password to LibrarianDAO

            if (librarianDAO.verifyUserPassword(userName, password)) {
                userNameLibrarian = userName;
            }
        } while (!librarianDAO.verifyUserPassword(userName, password));
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
        if (choice.equals("yes")) {
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
                System.out.println("Book created with following details:\n" + bookBatch.get(i));
            }
        } else {
            System.out.println("Process failed");
        }
    }

    //case 4
    public static void issueBook(LibraryDAO lib, Scanner console) {// Limit number of simultaneous book loans
        boolean stop = false;
        int sum = 0;

        Member borrower = null;
        Book book = null;
        String title = "", author = "";
        GregorianCalendar dueDate = getDueDate();
        int[] affectedRecords = new int[2];

        ArrayList<Book> ambiguousBooks = new ArrayList<>();
        ArrayList<Member> homonyms = new ArrayList<>();


        System.out.println("Provide Member ID or name: ");
        if (console.hasNextInt()) {
            int memberID = console.nextInt();
            borrower = lib.searchMember(memberID);
            if (borrower == null) {
                System.out.println("Member does not exist. Ask whether membership should be created");
            }
        } else {
            String name = console.next();
            homonyms = lib.searchMember(name);
            if (homonyms.size() == 1) {
                borrower = homonyms.get(0);
            } else if (homonyms.size() > 1) {
                for (Member member : homonyms) {
                    System.out.println(member);
                }
                System.out.println("Provide Member ID of correct member:");
                int choice = console.nextInt();
                for (Member member : homonyms) {
                    if (choice == member.getMemberID()) {
                        borrower = member;
                    }
                }
            } else {
                System.out.println("Member does not exist. Ask whether membership should be created");
            }
        }

        if (borrower.checkMembershipValidity(borrower) && (borrower != null)) {
            do {
                System.out.println("Choose search method to find book:\n "
                        + "1. Searching on BookID\n"
                        + "2. Searching on Title\n"
                        + "3. Searching on Author\n");
                int choice = console.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Provide BookID");
                        int bookID = console.nextInt();

                        book = lib.searchBook(bookID);// search on books
                        if (book != null && (book.getBookState() != BookStateEnum.ISSUED)) {
                            affectedRecords = lib.issueBook(borrower.getMemberID(), book, dueDate);
                            for (int i : affectedRecords) {
                                sum += i;
                            }
                        }
                        break;
                    case 2:
                        System.out.println("Provide title:");
                        title = console.next();
                        ambiguousBooks = lib.searchBook(title, author);
                        if (ambiguousBooks.size() == 1) {
                            book = ambiguousBooks.get(0);
                        } else if (ambiguousBooks.size() > 1) {
                            for (Book bookInAmbiguousBooks : ambiguousBooks) {
                                System.out.println(bookInAmbiguousBooks);
                            }
                            System.out.println("Provide Book ID of correct book");
                            int correctBook = console.nextInt();
                            for (Book bookInAmbiguousBooks : ambiguousBooks) {
                                if (correctBook == bookInAmbiguousBooks.getBook_ID()) {
                                    book = bookInAmbiguousBooks;
                                }
                            }
                        }
                        if (book != null && (book.getBookState() != BookStateEnum.ISSUED)) {
                            affectedRecords = lib.issueBook(borrower.getMemberID(), book, dueDate);
                            for (int i : affectedRecords) {
                                sum += i;
                            }
                        }
                        break;
                    case 3:
                        System.out.println("Author:");
                        author = console.next();
                        ambiguousBooks = lib.searchBook(title, author);
                        if (ambiguousBooks.size() == 1) {
                            book = ambiguousBooks.get(0);
                        } else if (ambiguousBooks.size() > 1) {
                            for (Book bookInAmbiguousBooks : ambiguousBooks) {
                                System.out.println(bookInAmbiguousBooks);
                            }
                            System.out.println("Provide Book ID of correct book");
                            int correctBook = console.nextInt();
                            for (Book bookInAmbiguousBooks : ambiguousBooks) {
                                if (correctBook == bookInAmbiguousBooks.getBook_ID()) {
                                    book = bookInAmbiguousBooks;
                                }
                            }
                        }
                        if (book != null && (book.getBookState() != BookStateEnum.ISSUED)) {
                            affectedRecords = lib.issueBook(borrower.getMemberID(), book, dueDate);
                            for (int i : affectedRecords) {
                                sum += i;
                            }
                        }
                        break;
                }
                if (sum == 2) {
                    System.out.println("Loan record inserted in db");
                } else {
                    System.out.println("Process failed");
                }
                if (book.getBookState() == BookStateEnum.ISSUED) {
                    System.out.println("Book already issued");
                }
                if (book == null) {
                    System.out.println("Book does not exist");
                }
                System.out.println("Issue more loans?");
                String s = console.next();

                if (s.equals("no")) {
                    stop = true;
                }
            } while (!stop);
        }
    }

    public static void returnBook(LibraryDAO lib, Scanner console) {
        boolean stop = false;
        int sum = 0;
        GregorianCalendar returnDate = new GregorianCalendar();
        GregorianCalendar dueDate = new GregorianCalendar();

        do {
            System.out.println("Provide BookID");
            int bookID = console.nextInt();

            Book book;

            book = lib.searchBook(bookID);// search on books
            if (book != null) {
                dueDate = lib.returnBook(book, returnDate);
                if (dueDate != null) {
                    System.out.println("Book return inserted in db ");
                } else {
                    System.out.println("Process failed");
                }
            } else {
                System.out.println("Book does not exist");
            }

            verifyDueDate(dueDate);

            System.out.println("Return more books?");
            String s = console.next();

            if (s.equals("no")) {
                stop = true;
            }

        } while(!stop);
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
        switch (member.getMembershipType()) {
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

    public static boolean verifyDueDate(GregorianCalendar dueDate) {
        GregorianCalendar currentDate = new GregorianCalendar();

        if (dueDate.compareTo(currentDate) < 0) {// negative if dueDate is before currentDate
            System.out.println("Book brought back after due date. Fine to be charged");
            return true;
        } else {
            System.out.println("Book brought back in time");
            return false;
        }
    }



    //case 8 restarts the login procedure
    //case 9 breaks out of the console interface loop and ends the program doing so
}