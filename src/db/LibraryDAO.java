package db;
import csvimport.CSVLoader;
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


             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);){

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
        try (Connection connection = getConn(); PreparedStatement statement = connection.prepareStatement(query);) {

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

            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    int retrievedMemberID = resultSet.getInt("Member_ID");
                    String name = resultSet.getString("LastName");
                    String address = resultSet.getString("Address");
                    int phone = resultSet.getInt("Phone");

                    member = new Member(retrievedMemberID, name, address, phone);

                }
            }
            return member;
        } catch (SQLException throwables) {
            System.out.println("Failure!");
            throwables.printStackTrace();

            return member;
        }
    }



    public ArrayList<Integer> addBook(ArrayList<Book> bookBatch) {
        ArrayList<Integer> primaryKeys = new ArrayList<Integer>();

        String query = "INSERT INTO Book" +
                "(Title, Author)" +
                "VALUES (?, ?)";

        try (Connection connection = getConn();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {

            for (Book book : bookBatch) {

                statement.setString(1, book.getTitle());
                statement.setString(2, book.getAuthor());

                statement.addBatch();
            }

            statement.executeBatch();

            try (ResultSet resultSet = statement.getGeneratedKeys();) {
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

    public void addBookcsv(String csvLoc) {
        try {

            CSVLoader loader = new CSVLoader(getConn());

            loader.loadCSV(csvLoc, "Book");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addBookcsv() {
        try {

            CSVLoader loader = new CSVLoader(getConn());

            loader.loadCSV("C:\\Users\\olivier.thas\\OneDrive - Dimension Data\\Documents\\Load sample1.csv", "Book");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Book searchBook(int bookID) {// parameters added to search on title or author. Send back multiple instances via ArrayList
        Book book = null;
        String query = "SELECT * " +
                "FROM Book " +
                "WHERE Book_ID = ?";

        try (Connection connection = getConn();
             PreparedStatement statement = connection.prepareStatement(query);) {

            statement.setInt(1, bookID);

            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    int retrievedBookID = resultSet.getInt("Book_ID");
                    String title = resultSet.getString("Title");
                    String author = resultSet.getString("Author");

                    book = new Book(retrievedBookID, title, author);
                }
            }
            return book;
        } catch (SQLException throwables) {
            System.out.println("Failure!");
            throwables.printStackTrace();

            return book;
        }
    }


    public int issueBook(int memberID, ArrayList<Book> bookBatch, GregorianCalendar parameterDueDate) {
        int affectedRecords = 0;
        Date dueDate = new Date(parameterDueDate.getTimeInMillis());
        String query = "INSERT INTO Borrowed_Book " +
                "(Book_ID, Member_ID, DueDate) " +
                "VALUES(?, ?, ?)";

        try (Connection connection = getConn();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {

            for (Book book : bookBatch) {

                statement.setInt(1, book.getBook_ID());
                statement.setInt(2, memberID);
                statement.setDate(3, dueDate);

                statement.addBatch();
            }

            int[] affectedR = statement.executeBatch();

            for(int num: affectedR) {
                affectedRecords += num;
            }

            return affectedRecords;

        } catch (SQLException e) {
            e.printStackTrace();
            return affectedRecords;
        }
    }
}

     /*public library.Member searchMember(int membershipID) {
         for (int i = 0; i < memberList.size(); i++) {
             if (memberList.get(i).getMemberID() == membershipID) {
                 return (library.Member)(memberList.get(i)); // syntax to be verified
             }
         }
         System.out.println("No match.");
         return null;
     }*/