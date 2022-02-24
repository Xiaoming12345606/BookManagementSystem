package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class BooksInfoManagementTools {
    static Connection connection = DbConnection.dbConnection;
    public static ResultSet titleSelectBooks(String text) {
        String text1 = '%' + text + '%';
        return SqlTools.executeQuery("select * from books where bno like ? or bname like ? or author like ? or publisher like ? or publish_time like ? or type like ?",new String[]{text1,text1,text1,text1,text1,text1},connection);
    }

    public static ResultSet selectBookOnType(String type) {
        return SqlTools.executeQuery("select * from books where type = '"+type+"'",connection);
    }

    public static ResultSet selectBooks(String bookName) {
        return SqlTools.executeQuery("select * from books where bname like ?",new String[]{bookName},connection);
    }

    public static ResultSet selectBooks() {
        return SqlTools.executeQuery("select * from books",connection);
    }

    public static ResultSet selectBooks(String bookName,String bookType) {
        return SqlTools.executeQuery("select * from books where bname like ? and type = ?",new String[]{bookName,bookType},connection);
    }

    public static boolean addBooks(String name, String author, String publisher, String publishTime, int sum_account, int left_account, String type) {
        String[] publishTimes = publishTime.split("-");
        String publish_time1 = publishTimes[0] + "年" + publishTimes[1] + "月";
        String publish_time2 = publishTimes[0] + publishTimes[1];
        ResourceBundle bundle = ResourceBundle.getBundle("bookType");
        return SqlTools.executeUpdate("insert into books values(?,?,?,?,?,?,?,?)", new int[]{5, 6},new Object[]{String.valueOf(type.charAt(0)) + '-' + publish_time2 + '-' + RandomUtil.getStr(),name,author,publisher, publish_time1,sum_account,left_account,bundle.getString(String.valueOf(type.charAt(0)))},connection);
    }

    public static boolean deleteBooks(String bookNo) {
        return SqlTools.executeUpdate("delete from books where bno = '"+bookNo+"'",connection);
    }

    public static boolean modifyBooks(String bookNo,String newName,String newPublisher,String newPublishTime,int sumAmount) {
        return SqlTools.executeUpdate("update books set bname = ? ,publisher = ?,publish_time = ?,sum_amount = ? where bno = ?",new int[]{3},new Object[]{newName,newPublisher,newPublishTime,sumAmount,bookNo},connection);
    }

    public static void setConnection(Connection connection) {
        BooksInfoManagementTools.connection = connection;
    }
}
