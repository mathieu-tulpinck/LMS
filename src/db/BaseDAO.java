package db;l

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDAO {

    private Connection conn = null;

    public Connection getConn() {
        try {
            if (conn == null || conn.isClosed())
                conn = DatabaseSingleton.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}