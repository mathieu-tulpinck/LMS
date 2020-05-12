import java.sql.*;
import java.util.*;

// class used to coordinate business logic
public class Library extends BaseDAO {

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

    public int addMember(Member member) {

        try (Connection connection = getConn();//try-with-resources statement
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Test" +
                         "(Name, Address, Phone)" +
                         " VALUES (?, ?, ?)",

                     Statement.RETURN_GENERATED_KEYS);) {

            statement.setString(1, member.getName());
            statement.setString(2, member.getAddress());
            statement.setInt(3, member.getPhone());

            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys();) {
                int primaryKey = 0;

                if (resultSet.next()) {
                    primaryKey = resultSet.getInt(1);
                }
                return primaryKey;
            }
        } catch (SQLException throwables) {
            System.out.println("Failure!");
            throwables.printStackTrace();

            return 0;
        }
    }

    public int updateMember(int memberID, String newAddress, int newPhone)  {
        String query;

        if(!newAddress.isEmpty()) {
            query = "UPDATE Test "
                    + "SET Address = ? "
                    + "WHERE MemberID = ?";
        } else {
            query = "UPDATE Test"
                    + "SET Phone=?"
                    + "WHERE MemberID = ?";
        }
        try (Connection connection = getConn(); PreparedStatement statement = connection.prepareStatement(query);) {

            if(!newAddress.isEmpty()) {
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



