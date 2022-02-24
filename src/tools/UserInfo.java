package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInfo {
    private Connection connection = DbConnection.dbConnection;

    public boolean logon(String accountNo,String userName, String password) {
        return SqlTools.executeUpdate("insert into reader values(?,?,?,0);",new String[]{accountNo,userName,password}, connection);
    }

    public boolean modifyPwd(String accountNo, String newPwd) {
        return SqlTools.executeUpdate("update reader set password = ? where account_no = ?",new String[]{newPwd,accountNo},connection);
    }

    public boolean logOff(String accountNO) {
        return SqlTools.executeUpdate("delete from reader where account_no = '"+accountNO+"'",connection);
    }

    public LoginInfo login(String account_number, String password) {
        ResultSet resultSet1 = SqlTools.executeQuery("select * from reader where account_no = ?;",new String[]{account_number},connection);
        try {
            if (resultSet1 != null && resultSet1.next()) {
                ResultSet resultSet2 = SqlTools.executeQuery("select * from reader where account_no = ? and password = ?;",new String[]{account_number,password},connection);
                if (resultSet2 != null && resultSet2.next()) {
                    return LoginInfo.SUCCESS;
                } else {
                    return LoginInfo.ERR_PWD;
                }
            } else {
                return LoginInfo.NULL_USER;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return LoginInfo.FAIL;
    }

    public ResultSet getUsername(String accountNo) {
        return SqlTools.executeQuery("select * from reader where account_no = ?",new String[]{accountNo},connection);
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
