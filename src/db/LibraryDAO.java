package db;

import library.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;

// class used to coordinate business logic
public class LibraryDAO extends BaseDAO {

    // constants regarding working of library. To be initialized.
    public final int reservationDuration = 0;
    private static volatile LibraryDAO instance = null;

    public LibraryDAO() {
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to create");
        }
    }

    public static LibraryDAO getInstance() {
        if (instance == null) {
            synchronized (LibraryDAO.class) {
                if (instance == null) {
                    instance = new LibraryDAO();
                }
            }
        }
        return instance;
    }


    public int addMember(Member member) {

        int primaryKey = 0;

        Date startDate = new Date(member.getStartDateMembership().getTimeInMillis());
        Date endDate = new Date(member.getEndDateMembership().getTimeInMillis());

        String query = "INSERT INTO Member" +
                "(MembershipType, LastName, Address, Phone, StartDate, EndDate)" +
                "VALUES (?, ?, ?, ?, ?, ?)";


        try (Connection connection = getConn();//try-with-resources statement


             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, member.getMembershipType().name());
            statement.setString(2, member.getLastName());
            statement.setString(3, member.getAddress());
            statement.setInt(4, member.getPhone());
            statement.setDate(5, startDate);
            statement.setDate(6, endDate);

            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys();) {

                if (resultSet.next()) {
                    primaryKey = resultSet.getInt(1);
                }
            }

            return primaryKey;

        } catch (SQLException throwables) {
            System.out.println("Failure!");
            throwables.printStackTrace();

            return 0;
        }
    }

    public int updateMember(int memberID, String newAddress, int newPhone) {
        String query;
        int result = 0;

        if (!newAddress.isEmpty()) {
            query = "UPDATE Member "
                    + "SET Address = ? "
                    + "WHERE Member_ID = ?";
        } else {
            query = "UPDATE Member "
                    + "SET Phone = ? "
                    + "WHERE Member_ID = ?";
        }
        try (Connection connection = getConn(); PreparedStatement statement = connection.prepareStatement(query)) {

            if (!newAddress.isEmpty()) {
                statement.setString(1, newAddress);
                statement.setInt(2, memberID);
                result = statement.executeUpdate();

                return result;
            } else {
                statement.setInt(1, newPhone);
                statement.setInt(2, memberID);
                result = statement.executeUpdate();

                return result;
            }
        } catch (SQLException throwables) {
            System.out.println("Failure!");
            throwables.printStackTrace();

            return 0;
        }
    }

    public Member searchMember(int memberID) {
        Member member = null;
        String query = "SELECT * " +
                "FROM Member " +
                "WHERE Member_ID = ?";

        try (Connection connection = getConn();
             PreparedStatement statement = connection.prepareStatement(query);) {

            statement.setInt(1, memberID);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int retrievedMemberID = resultSet.getInt("Member_ID");
                    MembershipEnum membershipType = MembershipEnum.valueOf(resultSet.getString("MembershipType"));
                    String name = resultSet.getString("LastName");
                    String address = resultSet.getString("Address");
                    int phone = resultSet.getInt("Phone");
                    Date retrievedStartDate = resultSet.getDate("StartDate");
                    Date retrievedEndDate = resultSet.getDate("EndDate");
                    GregorianCalendar startDate = new GregorianCalendar();
                    startDate.setTime(retrievedStartDate);
                    GregorianCalendar endDate = new GregorianCalendar();
                    endDate.setTime(retrievedEndDate);

                    member = new Member(retrievedMemberID, membershipType, name, address, phone, startDate, endDate);
                }
            }
            return member;
        } catch (SQLException throwables) {
            System.out.println("Failure!");
            throwables.printStackTrace();

            return member;
        }
    }

    public ArrayList<Member> searchMember(String name) {
        ArrayList<Member> members = new ArrayList<Member>();
        String query = "SELECT * " +
                "FROM Member " +
                "WHERE LastName = ?";

        try (Connection connection = getConn();
             PreparedStatement statement = connection.prepareStatement(query);) {

            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int memberID = resultSet.getInt("Member_ID");
                    MembershipEnum membershipType = MembershipEnum.valueOf(resultSet.getString("MembershipType"));
                    String retrievedName = resultSet.getString("LastName");
                    String address = resultSet.getString("Address");
                    int phone = resultSet.getInt("Phone");
                    Date retrievedStartDate = resultSet.getDate("StartDate");
                    Date retrievedEndDate = resultSet.getDate("EndDate");
                    GregorianCalendar startDate = new GregorianCalendar();
                    startDate.setTime(retrievedStartDate);
                    GregorianCalendar endDate = new GregorianCalendar();
                    endDate.setTime(retrievedEndDate);

                    Member member = new Member(memberID, membershipType, retrievedName, address, phone, startDate, endDate);
                    members.add(member);
                }
            }
            return members;
        } catch (SQLException throwables) {
            System.out.println("Failure!");
            throwables.printStackTrace();

            return members;
        }
    }


    public ArrayList<Integer> addBook(ArrayList<Book> bookBatch) {
        ArrayList<Integer> primaryKeys = new ArrayList<Integer>();

        String query = "INSERT INTO Book" +
                "(Title, Author)" +
                "VALUES (?, ?)";

        try (Connection connection = getConn();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            for (Book book : bookBatch) {

                statement.setString(1, book.getTitle());
                statement.setString(2, book.getAuthor());

                statement.addBatch();
            }

            statement.executeBatch();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    primaryKeys.add(resultSet.getInt(1));
                }
            }
            return primaryKeys;

        } catch (SQLException e) {
            e.printStackTrace();
            return primaryKeys;
        }
    }

    public Book searchBook(int bookID) {// parameters added to search on title or author. Send back multiple instances via ArrayList
        Book book = null;
        String query = "SELECT * " +
                "FROM Book " +
                "WHERE Book_ID = ?";

        try (Connection connection = getConn();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, bookID);

            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    int retrievedBookID = resultSet.getInt("Book_ID");
                    String title = resultSet.getString("Title");
                    String author = resultSet.getString("Author");
                    BookStateEnum bookState = BookStateEnum.valueOf(resultSet.getString("BookState"));

                    book = new Book(retrievedBookID, title, author, bookState);
                }
            }
            return book;
        } catch (SQLException throwables) {
            System.out.println("Failure!");
            throwables.printStackTrace();

            return book;
        }
    }

    public ArrayList<Book> searchBook(String title, String author) {
        Book book = null;
        ArrayList<Book> books = new ArrayList<>();
        String query;

        if (!title.isEmpty()) {
            query = "SELECT * " +
                    "FROM Book " +
                    "WHERE Title = ?";
        } else {
            query = "SELECT * " +
                    "FROM Book " +
                    "WHERE Author = ?";

        }

        try (Connection connection = getConn();
             PreparedStatement statement = connection.prepareStatement(query)) {

            if (!title.isEmpty()) {
                statement.setString(1, title);
                try (ResultSet resultSet = statement.executeQuery();) {
                    while (resultSet.next()) {
                        int retrievedBookID = resultSet.getInt("Book_ID");
                        String retrievedTitle = resultSet.getString("Title");
                        String retrievedAuthor = resultSet.getString("Author");
                        BookStateEnum bookState = BookStateEnum.valueOf(resultSet.getString("BookState"));

                        book = new Book(retrievedBookID, title, author, bookState);
                        books.add(book);
                    }
                }
                return books;
            } else {
                statement.setString(1, author);
                try (ResultSet resultSet = statement.executeQuery();) {
                    while (resultSet.next()) {
                        int retrievedBookID = resultSet.getInt("Book_ID");
                        String retrievedTitle = resultSet.getString("Title");
                        String retrievedAuthor = resultSet.getString("Author");
                        BookStateEnum bookState = BookStateEnum.valueOf(resultSet.getString("BookState"));

                        book = new Book(retrievedBookID, title, author, bookState);
                        books.add(book);
                    }
                }
                return books;

            }

        } catch (SQLException throwables) {
            System.out.println("Failure!");
            throwables.printStackTrace();

            return books;
        }
    }


    public int[] issueBook(int memberID, Book book, GregorianCalendar parameterDueDate) {
        int[] affectedRecord = new int[2];
        Date dueDate = new Date(parameterDueDate.getTimeInMillis());
        BookStateEnum bookState = BookStateEnum.ISSUED;

        String query1 = "INSERT INTO Borrowed_Book "
                + "(Book_ID, Member_ID, DueDate) "
                + "VALUES(?, ?, ?)";

        String query2 = "UPDATE Book "
                + "SET BookState = ? "
                + "WHERE Book_ID = ?";


        try (Connection connection = getConn();
             PreparedStatement statement1 = connection.prepareStatement(query1);
             PreparedStatement statement2 = connection.prepareStatement(query2)) {

            statement1.setInt(1, book.getBook_ID());
            statement1.setInt(2, memberID);
            statement1.setDate(3, dueDate);

            affectedRecord[0] = statement1.executeUpdate();

            statement2.setString(1, bookState.name());
            statement2.setInt(2, book.getBook_ID());

            affectedRecord[1] = statement2.executeUpdate();

            return affectedRecord;

        } catch (SQLException e) {
            e.printStackTrace();
            return affectedRecord;
        }
    }

    public GregorianCalendar returnBook(Book book, GregorianCalendar parameterReturnDate) {
        int borrowID;
        GregorianCalendar dueDate = new GregorianCalendar();
        Date returnDate = new Date(parameterReturnDate.getTimeInMillis());
        BookStateEnum bookState = BookStateEnum.AVAILABLE;

        String query1 = "SELECT *"
                + " FROM Borrowed_Book "
                + " WHERE Book_ID = ?";

        String query2 = "UPDATE Borrowed_Book"
                    + " Set ReturnDate = ? "
                    + " WHERE Borrow_ID = ?";

        String query3 = "UPDATE Book "
                + "SET BookState = ? "
                + "WHERE Book_ID = ?";


        try (Connection connection = getConn();
             PreparedStatement statement1 = connection.prepareStatement(query1);
             PreparedStatement statement2 = connection.prepareStatement(query2);
             PreparedStatement statement3 = connection.prepareStatement(query3)) {

            statement1.setInt(1, book.getBook_ID());

            try (ResultSet resultSet = statement1.executeQuery();) {
                resultSet.last();
                borrowID = resultSet.getInt("Borrow_ID");
                Date retrievedDueDate = resultSet.getDate("DueDate");
                dueDate.setTime(retrievedDueDate);
            }

            statement2.setDate(1, returnDate);
            statement2.setInt(2, borrowID);

            statement2.executeUpdate();

            statement3.setString(1, bookState.name());
            statement3.setInt(2, book.getBook_ID());

            statement3.executeUpdate();

            return dueDate;

        } catch (SQLException e) {
            e.printStackTrace();
            return dueDate;
        }
    }
}

