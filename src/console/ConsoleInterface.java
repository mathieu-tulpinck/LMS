package console;

import db.*;
import library.*;

import java.util.*;

public class ConsoleInterface {

    public static String userNameLibrarian;
    public static boolean userLoggedIn = false;
    public static final int LOAN_DURATION = 30;

    public static void main(String[] args) {

        LibraryDAO lib = LibraryDAO.getInstance();
        LibrarianDAO librarianDAO = new LibrarianDAO();
        MemberDAO memberDAO = new MemberDAO();

        Scanner console = new Scanner(System.in);
        console.useDelimiter(";|\r?\n|\r");

        int choice;

        //Login module, verify username & password
        login(librarianDAO, console);

        System.out.println("Access granted, welcome to the Library Management System!");

        //interface options after login
        do {
            System.out.println("Please make a choice between the following options (1 - 11):");
            System.out.println("1. Add member");
            System.out.println("2. Add librarian");
            System.out.println("3. Extend Membership");
            System.out.println("4. Modify details of a member");
            System.out.println("5. Add books");
            System.out.println("6. Mass upload of books (csv-file)");
            System.out.println("7. Show books");
            System.out.println("8. Issue books");
            System.out.println("9. Return books");
            System.out.println("10. Logout");
            System.out.println("11. Exit");

            choice = takeInput(console);

            switch (choice) {
                case 1:
                    addMember(lib, console);
                    break;

                case 2:
                    addLibrarian(librarianDAO, console);
                    break;

                case 3:
                    extendMembership(memberDAO, console);
                    break;

                case 4:
                    updateMember(lib, console);
                    break;

                case 5:
                    addBook(lib, console);
                    break;

                case 6:
                    loadCSVBooks(lib, console);
                    break;

                case 7:
                    showBooks(lib);
                    break;

                case 8:
                    issueBook(lib, console);
                    break;

                case 9:
                    returnBook(lib, console);
                    break;

                case 10:
                    userLoggedIn = false; //user gets logged out
                    System.out.println(userNameLibrarian + " successfully logged out");
                    login(librarianDAO, console);
                    break;

                case 11:
                    System.out.println("LMS shutting down...");
                    userLoggedIn = false;
                    break;

            }
        } while (userLoggedIn);
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
            userLoggedIn = librarianDAO.verifyUserPassword(userName, password);

            if (userLoggedIn) {
                userNameLibrarian = userName;
            } else {
                System.out.println("Login failed, try again with correct username & password");
            }
        } while (!userLoggedIn);
    }

    //safe input method
    public static int takeInput(Scanner console) {// safe input method to be designed
        int choice;
        System.out.println("Enter choice (1 - 11): ");
        choice = console.nextInt();
        while (!(choice > 0) || !(choice < 12)) {
            System.out.println("Input invalid. Try again");
            choice = console.nextInt();
        }
        return choice;
    }

    //case 1
    public static void addMember(LibraryDAO lib, Scanner console) {
        Member emptyMember = new Member();
        Member member = emptyMember.createMember(console);
        System.out.println("Can the member benefit from a reduction? yes / no");
        String choice = console.next();
        while (!choice.equals("yes") && !choice.equals("no")) {
            System.out.println("Invalid input. Try again");
            choice = console.next();
        }
        if (choice.equals("yes")) {
            member.chooseMembershipType(console, member);
        } // else member will keep default normal MembershipType
        int memberID = lib.addMember(member);
        member.setMemberID(memberID);

        if (memberID != -1) {
            System.out.println("library.Member created with following details:\n" + member);
        } else {
            System.out.println("Process failed.");
        }
    }

    //case 2
    public static void addLibrarian(LibrarianDAO lib, Scanner console) {
        Librarian librarian = Librarian.createLibrarian(console);
        int librarianID = lib.addLibrarian(librarian);
        librarian.setLibrarianID(librarianID);

        if (librarianID != 0) {
            System.out.println("Librarian created with following details:\n" + librarian);
        } else {
            System.out.println("Librarian not created, please try again");
        }
    }

    //case 3
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

    //case 4
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
        if (lib.updateMember(memberID, newAddress, newPhone) != -1) {
            System.out.println("Details of member " + memberID + " updated.");
        } else {
            System.out.println("Process failed");
        }
    }

    //case 5
    public static void addBook(LibraryDAO lib, Scanner console) {
        Book book = new Book();
        boolean stop = false;
        ArrayList<Book> bookBatch = new ArrayList<Book>();
        ArrayList<Integer> bookIDs;
        do {
            book = book.createBook(console);
            bookBatch.add(book);// only add if book != null

            System.out.println("Add more books? yes / no");
            String s = console.next();

            while (!s.equals("yes")) {
                if (s.equals("no")) {
                    stop = true;
                    break;
                } else {
                    System.out.println("Invalid input. Try again");
                    s = console.next();
                }
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

    //case 6
    public static void loadCSVBooks(LibraryDAO lib, Scanner console) {
        try {
            console.useDelimiter(";|\r?\n|\r");
            System.out.println("Enter the csv file (no spaces) and its location: (e.g. C:\\Users\\olivier.thas\\Downloads\\Loadsample1.csv)");
            System.out.println("Consider a csv format with header columns Title, Author and BookState");
            String csvLocation = console.next();
            lib.addBookcsv(csvLocation);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //case 7
    public static void showBooks(LibraryDAO lib) {
        lib.showBooks();
    }

    //case 8
    public static void issueBook(LibraryDAO lib, Scanner console) {
        boolean stop = false;
        int sum = 0;
        Member borrower = null;
        Book book = null;
        String title = "", author = "";
        GregorianCalendar dueDate = getDueDate();
        int[] affectedRecords;
        ArrayList<Book> ambiguousBooks;
        ArrayList<Member> homonyms;


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
            }
        }

        if (borrower == null) {
            System.out.println("Member does not exist. Ask whether membership should be created");
        } else if (borrower.checkMembershipValidity(borrower)) {
            do {
                System.out.println("Choose search method to find book (1 - 3):");
                System.out.println("1. Searching on BookID");
                System.out.println("2. Searching on Title");
                System.out.println("3. Searching on Author");
                int choice = console.nextInt();
                while (!(choice > 0) || !(choice < 4)) {
                    System.out.println("Input invalid. Try again");
                    choice = console.nextInt();
                }
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
                        } else {
                            sum -= 1;
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
                        } else {
                            sum -= 1;
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
                        } else {
                            sum -= 1;
                        }
                        break;
                }
                if (sum % 2 == 0) {
                    System.out.println("Loan record inserted in db");

                } else {
                    System.out.println("Process failed");
                    if (book == null) {
                        System.out.println("Book does not exist");
                    } else if (book.getBookState() == BookStateEnum.ISSUED) {
                        System.out.println("Book already issued");
                    }
                }

                System.out.println("Issue more loans?");
                String s = console.next();

                while (!s.equals("yes")) {
                    if (s.equals("no")) {
                        stop = true;
                        break;
                    } else {
                        System.out.println("Invalid input. Try again");
                        s = console.next();
                    }
                }
            } while (!stop);
        }
    }

    // case 9
    public static void returnBook(LibraryDAO lib, Scanner console) {
        boolean stop = false;
        GregorianCalendar returnDate = new GregorianCalendar();
        GregorianCalendar dueDate;
        Book book;

        do {
            System.out.println("Provide BookID");
            int bookID = console.nextInt();

            book = lib.searchBook(bookID);// search on books
            if (book != null) {
                dueDate = lib.returnBook(book, returnDate);
                if (dueDate.isSet(GregorianCalendar.YEAR)) {
                    System.out.println("Book return inserted in db ");
                    verifyDueDate(dueDate);
                } else {
                    System.out.println("Process failed");
                }
            } else {
                System.out.println("Book does not exist");
            }

            System.out.println("Return more books?");
            String s = console.next();

            while (!s.equals("yes")) {
                if (s.equals("no")) {
                    stop = true;
                    break;
                } else {
                    System.out.println("Invalid input. Try again");
                    s = console.next();
                }
            }
        } while (!stop);
    }


    //initialize calendar for loans
    public static GregorianCalendar getDueDate() {
        GregorianCalendar dueDate = new GregorianCalendar();
        dueDate.add(GregorianCalendar.DATE, LOAN_DURATION);

        return dueDate;
    }

    // verify whether book was returned in time
    public static void verifyDueDate(GregorianCalendar dueDate) {
        GregorianCalendar currentDate = new GregorianCalendar();

        if (dueDate.compareTo(currentDate) < 0) {
            if (dueDate.compareTo(currentDate) < 0) {
                System.out.println("Book brought back after due date. Fine to be charged");
            } else {
                System.out.println("Book brought back in time");
            }
        }
    }
}