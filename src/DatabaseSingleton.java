import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseSingleton {

    private Connection connection;
    private static DatabaseSingleton instance = null;

    public DatabaseSingleton() {
    }

    public static DatabaseSingleton getInstance(){
        if (instance == null)
            instance = new DatabaseSingleton();
        return instance;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()){
                connection = DriverManager.getConnection("jdbc:mysql://dt5.ehb.be/1920mobappgr2?useSSL=false","1920mobappgr2","jWqB5N4");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
