import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainOlivier {
    public static void main(String[] args) {
        try {
            //Register JDBC Driver
            Class.forName(com.mysql.jdbc.Driver);

            Connection connection = DriverManager.getConnection("jdbc:mysql://dt5.ehb.be/1920mobappgr2","1920mobappgr2","jWqB5N4");
        }
        catch (ClassNotFoundException error1) {
            System.out.println("Error: unable to load driver class!");
            System.exit(1);
        }
        catch (SQLException error2) {
            System.out.println("Error: other error!");
            System.exit(2);
        }

    }

    private static void getConnection() {
    }
}
