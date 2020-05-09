import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainOlivier {
    public static void main(String[] args) {
        try {
            //Laden van een Driver --> Register JDBC Driver
            Class.forName("com.mysql.jdbc.Driver");
            // verbinden met database --> class instance from class Connection (e.g. "connection") is set using method getConnection (from class DriverManager
            Connection connection = DriverManager.getConnection("jdbc:mysql://dt5.ehb.be/1920mobappgr2","1920mobappgr2","jWqB5N4");
            if (connection != null) {
                    connection.close();
                }
        }
        catch (ClassNotFoundException error1) {
            System.out.println("Error: unable to load driver class!");
            System.exit(1);
        }
        catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorCode: " + ex.getErrorCode());
            System.out.println("Cause: " + ex.getCause());
            System.exit(2);
        }
    }

    private static void getConnection() {
    }
}
