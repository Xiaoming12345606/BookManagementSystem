package user;

import tools.DbConnection;
import java.sql.Connection;

public class User {
    String accountNo;
    String userName;
    String password;
    Connection connection = DbConnection.dbConnection;

    public User() {
    }

    public User(String accountNo, String password) {
        this.accountNo = accountNo;
        this.password = password;
    }

    public User(String accountNo, String userName, String password) {
        this.accountNo = accountNo;
        this.userName = userName;
        this.password = password;
    }



    public String getAccountNo() {
        return accountNo;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
