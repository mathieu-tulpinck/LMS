package db;

import java.sql.*;

public class LibrarianDAO extends BaseDAO {

   public boolean verifyUserPassword(String username, String password) {
        try (Connection c = getConn()) {
            Statement s = c.createStatement();
            String stringQuery = "SELECT count(*) AS rowcount FROM Librarian WHERE username = '" + username + "' AND password = '" + password + "'";
            //System.out.println(stringQuery);
            ResultSet rs = s.executeQuery(stringQuery);
            int count = 0;
            while (rs.next()) {
                count = rs.getInt("rowcount");
            }
            if (count == 0) {
                System.out.println("Wrong username or password, please try again");
                return false;
            } else {
                //Correct username and password
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Something went wrong, please try again");
            return false;
        }
    }
}