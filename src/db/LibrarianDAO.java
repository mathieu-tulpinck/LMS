package db;

import java.math.BigDecimal;
import java.sql.*;

public class LibrarianDAO extends BaseDAO {

    /*public void loadLibrarian (String username, String password)
    {
        try(Connection c = getConn()){
            Statement s = c.createStatement();
            int result = s.executeUpdate("insert into Product values(NULL ,'"+ product.getNaam() +"' ,"+product.getPrijs() +")");
            if(result>0)
                System.out.println("Gelukt!");
            else System.out.println("MISLUKT!");
        } catch (SQLException throwables){
            throwables.printStackTrace();
            System.out.println("MISLUKT!!");
        }
    }*/

    public boolean verifyUserPassword(String username, String password) {
        try (Connection c = getConn()) {
            Statement s = c.createStatement();
            String stringQuery = "SELECT count(*) AS rowcount FROM Librarian WHERE username = '" + username + "' AND password = '" + password + "'";
            //System.out.println(stringQuery);
            ResultSet rs = s.executeQuery(stringQuery);
            int count = 0;
            while (rs.next()) {
                // retrieve and print the values for the current row
                count = rs.getInt("rowcount");
            }
            if (count == 0) {
                System.out.println("Wrong username or password");
                return false;
            } else {
                //System.out.println("Correcte username en paswoord");
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Wrong username or password!");
            return false;
        }
    }




    public void loadLibrarian (String username, String password)
    {
        try(Connection c = getConn()){
            Statement s = c.createStatement();
            String stringQuery = "SELECT Librarian_ID, LastName, FirstName FROM Librarians WHERE username = '" + username + "' AND password = '" + password + "'";
            System.out.println(stringQuery);
            ResultSet rs = s.executeQuery(stringQuery);
            while (rs.next()) {
                // retrieve and print the values for the current row
                int i = rs.getInt("Librarian_ID");
                String st = rs.getString("LastName");
                String str = rs.getString("FirstName");
                System.out.println("ROW = " + i + " " + st + " " + str);
            }

        } catch (SQLException throwables){
            throwables.printStackTrace();
            System.out.println("Gebruiker of wachtwoord onjuist!");
        }
    }
}