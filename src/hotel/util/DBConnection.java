package hotel.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/hotel_db"; // your DB URL
    private static final String USER = "root"; // your DB username
    private static final String PASSWORD = "Aiwa@1002"; // your DB password

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");  // Load MySQL driver
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
