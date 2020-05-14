package db;

import java.sql.*;
import java.util.*;
import java.util.Date;

import library.*;

public class MemberDAO extends BaseDAO {
    /*public int getMembershipId (int memberId)
    {
        try(Connection c = getConn()){
            Statement s = c.createStatement();
            String stringQuery = "SELECT Membership_ID FROM Member WHERE Member_ID =  " + memberId;
            System.out.println(stringQuery);
            ResultSet rs = s.executeQuery(stringQuery);
            while (rs.next()) {
                // retrieve and print the values for the current row
                int i = rs.getInt("Membership_ID");
                System.out.println("Membership_ID = " + i);
                return i;
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
            System.out.println("Problem!");
        }
        return 0;
    } */

    public Membership getMembership (int memberId)
    {
        try(Connection c = getConn()){
            Statement s = c.createStatement();
            String stringQuery = "SELECT Membership_ID, MembershipType, Price, StartDate, EndDate FROM Membership WHERE Member_ID =  " + memberId;
            //System.out.println(stringQuery);
            ResultSet rs = s.executeQuery(stringQuery);
            while (rs.next()) {
                // retrieve and print the values for the current row
                int membershipId = rs.getInt("Membership_ID");
                String membershipType = rs.getString("MembershipType");
                int price = rs.getInt("Price");
                Date startDate = rs.getDate("StartDate");
                Date endDate = rs.getDate("EndDate");

                /*System.out.println("Membership_ID = " + membershipId );
                System.out.println("MembershipType = " + membershipType );
                System.out.println("Price = " + price );
                System.out.println("Start date = " + startDate );
                System.out.println("End Date = " + endDate );*/

                GregorianCalendar startGregorianCalendar = new GregorianCalendar();
                startGregorianCalendar.setTime(startDate);

                GregorianCalendar endGregorianCalendar = new GregorianCalendar();
                startGregorianCalendar.setTime(endDate);

                Membership membership = new Membership(membershipId, memberId, MembershipType.NORMAL, price, startGregorianCalendar, endGregorianCalendar );

                return membership;
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
            System.out.println("Problem!");
        }
        return null;
    }



    public int extendMembershipYear (int membershipId)
    {
        String query;
        int result = 0;

        query = "UPDATE Membership "
                + "SET EndDate = DATE_ADD(EndDate, INTERVAL 1 YEAR) "
                + "WHERE Membership_ID = ?";

        try (Connection connection = getConn(); PreparedStatement statement = connection.prepareStatement(query);) {

                statement.setInt(1, membershipId); //Pass membershipID
                result = statement.executeUpdate();

                return result;

        } catch (SQLException throwables) {
            System.out.println("Failure!");
            throwables.printStackTrace();

            return 0;
        }
    }
}
