package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbSingleton {

    private Connection connection;
    private static dbSingleton instance = null;

    public dbSingleton() {
    }

    public static dbSingleton getInstance(){
        if (instance == null)
            instance = new dbSingleton();
        return instance;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()){
                connection = DriverManager.getConnection("jdbc:mysql://dt5.ehb.be/1920PROGESS090","1920PROGESS090","41862573");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
