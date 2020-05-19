
package db;

import java.sql.*;
import java.util.*;
import java.util.Date;

import library.*;

public class MemberDAO extends BaseDAO {

    public Member getMember(int memberId) {
        try (Connection c = getConn()) {
            Statement s = c.createStatement();
            String stringQuery = "SELECT Member_ID, MembershipType, StartDate, EndDate FROM Member WHERE Member_ID =  " + memberId;
            ResultSet rs = s.executeQuery(stringQuery);
            while (rs.next()) {
                // retrieve and print the values for the current row
                int memberId2 = rs.getInt("Member_ID");
                String membershipType = rs.getString("MembershipType");
                Date startDate = rs.getDate("StartDate");
                Date endDate = rs.getDate("EndDate");

                GregorianCalendar startGregorianCalendar = new GregorianCalendar();
                startGregorianCalendar.setTime(startDate);

                GregorianCalendar endGregorianCalendar = new GregorianCalendar();
                startGregorianCalendar.setTime(endDate);


                Member member = new Member(memberId, membershipType, startGregorianCalendar, endGregorianCalendar);

                return member;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Problem!");
        }
        return null;
    }

    //Extend End Date membership with 1 year and display price to pay
    public int extendMembershipYear(int memberId) {
        String query;
        int result = 0;

        query = "UPDATE Member "
                + "SET EndDate = DATE_ADD(EndDate, INTERVAL 1 YEAR) "
                + "WHERE Member_ID = ?";

        try (Connection connection = getConn(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, memberId); //Pass memberID
            result = statement.executeUpdate();

            return result;

        } catch (SQLException throwables) {
            System.out.println("Something went wrong, please try again");
            throwables.printStackTrace();

            return 0;
        }
    }

}

