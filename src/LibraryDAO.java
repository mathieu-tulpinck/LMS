import java.sql.*;
import java.sql.Date;
import java.util.*;

// class used to coordinate business logic
public class LibraryDAO extends BaseDAO {

    public final int LOAN_DURATION = 30;// constants regarding working of library. To be initialized.
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

    public Member createMember(Scanner console) {


        System.out.println("Provide name");
        String name = console.next();

        System.out.println("Provide address");
        String address = console.next();

        System.out.println("Provide phone");
        int phone = console.nextInt();

        Member member = new Member(name, address, phone);

        return member;
    }

    public Membership createMembership(Scanner console) {

        int choice;
        MembershipType m;
        System.out.println("Provide type of membership:");// user input should be restricted to enums
        choice = console.nextInt();
        switch (choice) {
            case 1:
                m = MembershipType.JUNIOR;
                break;
            case 2:
                m = MembershipType.STUDENT;
                break;
            case 3:
                m = MembershipType.SENIOR;
                break;
            default:
                m = MembershipType.NORMAL;
                break;
        }

        Membership membership = new Membership(m);

        return membership;
    }

    public int addMember(Member member, Membership membership) {

        int primaryKey = 0;
        MembershipType membershipType = membership.getMembershipType();
        Date startDate = new Date(membership.getStartDate().getTimeInMillis());
        Date endDate = new Date(membership.getEndDate().getTimeInMillis());

        String query1 = "INSERT INTO Member" +
                "(LastName, Address, Phone)" +
                "VALUES (?, ?, ?)";

        String query2 = "INSERT INTO Membership" +
                "(Member_ID, MembershipType, Price, StartDate, EndDate)" +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConn();//try-with-resources statement


             PreparedStatement statement1 = connection.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement statement2 = connection.prepareStatement(query2);) {

            statement1.setString(1, member.getName());
            statement1.setString(2, member.getAddress());
            statement1.setInt(3, member.getPhone());

            statement1.executeUpdate();

            try (ResultSet resultSet = statement1.getGeneratedKeys();) {

                if (resultSet.next()) {
                    primaryKey = resultSet.getInt(1);
                }
            }
            statement2.setInt(1, primaryKey);// probably smthg cleaner possible (SQL trigger on insert?)
            statement2.setString(2, membershipType.name());
            statement2.setInt(3, membership.getPrice());// to be changed to double
            statement2.setDate(4, startDate);
            statement2.setDate(5, endDate);

            statement2.executeUpdate();

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

    public Book createBook(Scanner console) {

        String title, author;

        System.out.println("Provide title:");
        title = console.next();
        System.out.println("Provide author:");
        author = console.next();

        Book book = new Book(title, author);
        return book;
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

    public Book searchBook(int bookID) {
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

    public int issueBook(int memberID, ArrayList<Book> bookBatch) {
        int affectedRecords = 0;
        Date dueDate = new Date(getDueDate().getTimeInMillis());
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

    public GregorianCalendar getDueDate() {
        GregorianCalendar dueDate = new GregorianCalendar();
        dueDate.add(GregorianCalendar.DATE, LOAN_DURATION);

        return dueDate;
    }


}

     /*public Member searchMember(int membershipID) {
         for (int i = 0; i < memberList.size(); i++) {
             if (memberList.get(i).getMemberID() == membershipID) {
                 return (Member)(memberList.get(i)); // syntax to be verified
             }
         }
         System.out.println("No match.");

         return null;
     }*/



