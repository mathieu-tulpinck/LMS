import java.sql.*;
import java.sql.Date;
import java.util.*;

// class used to coordinate business logic
public class LibraryDAO extends BaseDAO {

    public final int borrowDuration = 0;// constants regarding working of library. To be initialized.
    public final int reservationDuration = 0;

    // constructor design to be modified to match singleton pattern

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
        String membershipType = membership.getMembershipType().name();
        Date startDate = new Date(membership.getStartDate().getTimeInMillis());
        Date endDate = new Date(membership.getEndDate().getTimeInMillis());

        String query1 = "INSERT INTO Member" +
                "(LastName, Address, Phone)" +
                "VALUES (?, ?, ?)";

        String query2 = "INSERT INTO Membership" +
                "(Member_ID, MembershipType, Price, StartDate)" +
                "VALUES (?, ?, ?, ?)";

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
            statement2.setString(2, membershipType);
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

        if (!newAddress.isEmpty()) {
            query = "UPDATE Test "
                    + "SET Address = ? "
                    + "WHERE MemberID = ?";
        } else {
            query = "UPDATE Test"
                    + "SET Phone=?"
                    + "WHERE MemberID = ?";
        }
        try (Connection connection = getConn(); PreparedStatement statement = connection.prepareStatement(query);) {

            if (!newAddress.isEmpty()) {
                statement.setString(1, newAddress);
                statement.setInt(2, memberID);
                int result = statement.executeUpdate();

                return result;
            } else {
                statement.setInt(1, newPhone);
                statement.setInt(2, memberID);
                int result = statement.executeUpdate();

                return result;
            }
        } catch (SQLException throwables) {
            System.out.println("Failure!");
            throwables.printStackTrace();

            return 0;
        }


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



