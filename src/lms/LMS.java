package lms;

import thread.TimeOutThread;
import tools.DbConnection;
import tools.DatabaseConnection;
import tools.PageSwitch;
import ui.LoginFrame;
import ui.LogonFrame;
import ui.MainFrame;
/**
 * @author smh
 */
public class LMS {
    public static void main(String[] args) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.getConnection();
        DbConnection.dbConnection = databaseConnection.connection;
        DbConnection.databaseConnection = databaseConnection;
        PageSwitch.loginFrame = new LoginFrame();
        PageSwitch.logonFrame = new LogonFrame();
        PageSwitch.mainFrame = new MainFrame();
//        TimeOutThread timeOutThread = new TimeOutThread();
//        timeOutThread.setDaemon(true);
//        timeOutThread.start();
        PageSwitch.loginFrame.setVisible(true);
    }
}
