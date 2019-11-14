package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionSingleton {

    private static ConnectionSingleton s;
    private static String              dbName = "testpersonne";
    private        Connection          c;

    private ConnectionSingleton() {
        try {
            String url = "jdbc:mysql://pa1007.fr/" + dbName;
            c = DriverManager.getConnection(url, "JDBCcours", "12345679");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return c;
    }

    public static synchronized Connection getInstance() {
        if (s == null) {
            s = new ConnectionSingleton();
        }
        else {
            try {
                if (s.c != null && s.c.isClosed()) {
                    s = new ConnectionSingleton();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return s.getConnection();
    }

    public static void setDbName(String dbName) {
        ConnectionSingleton.dbName = dbName;
        s = null;
    }
}
