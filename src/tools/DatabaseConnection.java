package tools;

import java.sql.*;
import java.util.ResourceBundle;

public class DatabaseConnection {
    public Connection connection;

    public DatabaseConnection() {

    }

    public void getConnection() {

        ResourceBundle bundle = ResourceBundle.getBundle("connectionInfo");
        try {
            if (connection != null) {
                connection.close();
            }
            Class.forName(bundle.getString("driver"));
            connection = DriverManager.getConnection(bundle.getString("URL"),bundle.getString("user"),bundle.getString("password"));
            connection.setAutoCommit(false);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disconnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
