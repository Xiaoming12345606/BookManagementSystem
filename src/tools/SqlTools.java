package tools;

import java.sql.*;

public class SqlTools {
    public static ResultSet executeQuery(String sql, String[] placeholderNumbers, Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            for (int i = 0; i < placeholderNumbers.length; i++) {
                ps.setString(i + 1, placeholderNumbers[i]);
            }
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet executeQuery(String sql,Connection connection) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean executeUpdate(String sql,Connection connection) {
        try {
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean executeUpdate(String sql,String[] placeholderNumbers, Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            for (int i = 0; i < placeholderNumbers.length; i++) {
                ps.setString(i + 1, placeholderNumbers[i]);
            }
            int rows = ps.executeUpdate();
            if (rows > 0) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean executeUpdate(String sql,int[] intIndex,Object[] placeholderNumbers, Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            for (int i = 0; i < placeholderNumbers.length; i++) {
                for (Integer integer : intIndex) {
                    if (i == integer) {
                        ps.setInt(i + 1, Integer.parseInt(String.valueOf(placeholderNumbers[i])));
                    } else {
                        ps.setString(i + 1, String.valueOf(placeholderNumbers[i]));
                    }
                }
            }
            int rows = ps.executeUpdate();
            if (rows > 0) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
