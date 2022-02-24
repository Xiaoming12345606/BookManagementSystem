package user;

import tools.SqlTools;

import java.sql.ResultSet;

public class Reader extends User {

    public Reader(String accountNo, String userName, String password) {
        super(accountNo, userName, password);
    }

    public ResultSet selectBooksForOwn() {
        return SqlTools.executeQuery("select b.bno,type,bname,author,publisher,publish_time,br.renew from books b join borrow br on b.bno = br.bno and br.account_no = '"+this.accountNo+"'",connection);
    }

    public ResultSet selectBooksForOwn(String bookName) {
        return SqlTools.executeQuery("select b.bno,type,bname,author,publisher,publish_time,br.renew from books b join borrow br on b.bno = br.bno and br.account_no = '"+this.accountNo+"' where bname = ?",new String[]{bookName},connection);
    }

    public ResultSet selectBooksForOwnByType(String bookType) {
        return SqlTools.executeQuery("select b.bno,type,bname,author,publisher,publish_time,br.renew from books b join borrow br on b.bno = br.bno and br.account_no = '"+this.accountNo+"' where type = ?",new String[]{bookType},connection);
    }

    public ResultSet selectBooksForOwn(String bookName,String bookType) {
        return SqlTools.executeQuery("select b.bno,type,bname,author,publisher,publish_time,br.renew from books b join borrow br on b.bno = br.bno and br.account_no = '"+this.accountNo+"' where bname = ? and type = ?",new String[]{bookName,bookType},connection);
    }

    public boolean borrowBooks(String bookNo, String borrowTime) {
        if (SqlTools.executeUpdate("insert into borrow values('"+this.accountNo+"',?,?,'否');",new String[]{bookNo,borrowTime},connection)) {
            return SqlTools.executeUpdate("update books set left_amount = left_amount - 1 where bno = '" + bookNo + "';", connection);
        }
        return false;
    }

    public boolean returnBooks(String bookNo) {
        if (SqlTools.executeUpdate("delete from borrow where bno = '"+bookNo+"' and account_no = '"+this.accountNo+"';",connection)) {
            return SqlTools.executeUpdate("update books set left_amount = left_amount + 1 where bno = '" + bookNo + "';", connection);
        }
        return false;
    }

    public boolean renewBook(String bookNo) {
        return SqlTools.executeUpdate("update borrow set renew = '是' where bno = '"+bookNo+"' and account_no = '"+this.accountNo+"';",connection);
    }
}
