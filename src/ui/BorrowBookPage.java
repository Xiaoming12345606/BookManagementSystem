package ui;

import tools.BooksInfoManagementTools;
import tools.ImageInput;
import tools.TableInfoTools;
import user.Reader;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.Vector;

public class BorrowBookPage extends JPanel {
    private Reader reader;
    JPanel head = new JPanel();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();

    JLabel bookName = new JLabel("图书名称：");
    JLabel bookNo = new JLabel("图书编号：");
    JLabel returnTime = new JLabel("归还时间：");
    JLabel bookType = new JLabel("图书种类：");
    JLabel headLabel = new JLabel("图书信息");
    JComboBox<String> bookTypeCombo1 = new JComboBox<>();

    JTextField bookNameField = new JTextField();
    JLabel bookNameLabel = new JLabel();
    JTextField returnTimeField = new JTextField();

    JButton borrow = new JButton("借阅");
    JButton goBack = new JButton("返回");
    JButton search = new JButton("搜索");
    JButton view = new JButton("显示所有");

    JTable table = new JTable();
    JScrollPane scrollPane = new JScrollPane(table);
    DefaultTableModel model = new DefaultTableModel();

    ResourceBundle bundle = ResourceBundle.getBundle("bookType");

    public BorrowBookPage() {
        this.setPreferredSize(new Dimension(1000, 560));
        controlInit();

        setBookTypeComboBox();
        tableInit();
        listeners();
        this.add(head);
        this.add(panel1);
        this.add(panel2);
//        this.add(panel3);
        this.add(panel4);
    }

    public void controlInit() {
        head.setPreferredSize(new Dimension(1000,40));
        headLabel.setFont(new Font("microsoft YaHei",Font.BOLD,20));
        headLabel.setIcon(new ImageIcon(new ImageInput(new File("src/images/bookInfo.png")).getImage()));
        head.add(headLabel);
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));
        bookNameField.setPreferredSize(new Dimension(150, 30));
        bookNameField.setBorder(new LineBorder(new Color(0x7DC3F1), 1, true));
        panel1.add(bookName);
        panel1.add(bookNameField);
        bookTypeCombo1.setPreferredSize(new Dimension(150, 30));
        panel1.add(bookType);
        panel1.add(bookTypeCombo1);
        search.setFocusPainted(false);
        search.setFont(new Font("microsoft YaHei", Font.PLAIN, 12));
        search.setPreferredSize(new Dimension(50, 30));
        search.setBorder(new LineBorder(new Color(0x7DC3F1), 1, true));
        view.setFocusPainted(false);
        view.setFont(new Font("microsoft YaHei", Font.PLAIN, 12));
        view.setPreferredSize(new Dimension(50, 30));
        view.setBorder(new LineBorder(new Color(0x7DC3F1), 1, true));
        panel1.add(search);
        panel1.add(view);

        model.addColumn("图书编号");
        model.addColumn("图书种类");
        model.addColumn("图书名称");
        model.addColumn("作者");
        model.addColumn("出版社");
        model.addColumn("出版时间");
        model.addColumn("总量");
        model.addColumn("剩余");
        scrollPane.setPreferredSize(new Dimension(900, 300));
        table.setModel(model);
        panel2.add(scrollPane, BorderLayout.CENTER);
        panel2.setBorder(new LineBorder(new Color(0x7DC3F1),1,true));
        tableStyle();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panel3.setPreferredSize(new Dimension(600, 60));
        bookNameLabel.setPreferredSize(new Dimension(150, 30));
        returnTimeField.setPreferredSize(new Dimension(150, 30));
        returnTimeField.setBorder(new LineBorder(new Color(0x7DC3F1), 1, true));

        bookNameLabel.setFont(new Font("microsoft YaHei",Font.BOLD,16));
        panel3.add(bookNo);
        panel3.add(bookNameLabel);
        panel3.add(returnTime);
        panel3.add(returnTimeField);

        borrow.setFocusPainted(false);
        goBack.setFocusPainted(false);
        borrow.setPreferredSize(new Dimension(100,30));
        goBack.setPreferredSize(new Dimension(60,30));
        borrow.setBorder(new LineBorder(new Color(0x7DC3F1), 1, true));
        goBack.setBorder(new LineBorder(new Color(0x7DC3F1), 1, true));
        panel4.setLayout(new FlowLayout(FlowLayout.CENTER,100,30));
        panel4.setPreferredSize(new Dimension(1000,100));
        panel4.add(borrow);
    }

    public void listeners() {
        search.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String bookName = bookNameField.getText();
                String bookType1 = (String) bookTypeCombo1.getSelectedItem();
                String bookType;
                if ("".equals(bookName) && "".equals(bookType1)) {
                    JOptionPane.showMessageDialog(new JFrame(),"请先输入查询条件！");
                    return;
                }
                if (!"".equals(bookType1)) {
                    assert bookType1 != null;
                    bookType = String.valueOf(bookType1.charAt(0));
                    tableInit(bookName,bookType);
                } else {
                    tableInit(bookName,"");
                }
                table.updateUI();
            }
        });

        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableInit();
            }
        });

        borrow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                borrowBook(row);
            }
        });
    }

    public void tableInit() {
        ResultSet resultSet = BooksInfoManagementTools.selectBooks();
        model.getDataVector().removeAllElements();
        table.getSelectionModel().clearSelection();
        tableInitRow(resultSet);
    }



    public void tableStyle() {
        DefaultTableCellRenderer r=new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class,r);
        TableInfoTools.fitTableColumns(table);
    }

    public void tableInit(String bookName,String bookType) {
        ResultSet resultSet;
        model.getDataVector().removeAllElements();
        table.getSelectionModel().clearSelection();
        if ("".equals(bookName)) {
            resultSet = BooksInfoManagementTools.selectBookOnType(bundle.getString(bookType));
        } else if ("".equals(bookType)) {
            resultSet = BooksInfoManagementTools.selectBooks("%" + bookName + "%");
        }else {
            resultSet = BooksInfoManagementTools.selectBooks("%" + bookName + "%",bundle.getString(bookType));
        }
        tableInitRow(resultSet);
    }

    public void tableInitRow(ResultSet resultSet) {

        try {
            while (resultSet.next()) {
                Vector<java.io.Serializable> vector = new Vector<>();
                vector.add(resultSet.getString(1));
                vector.add(resultSet.getString(8));
                vector.add(resultSet.getString(2));
                vector.add(resultSet.getString(3));
                vector.add(resultSet.getString(4));
                vector.add(resultSet.getString(5));
                vector.add(resultSet.getInt(6));
                vector.add(resultSet.getInt(7));

                model.addRow(vector);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void tableInitRowBySearch(ResultSet resultSet) {
        model.getDataVector().removeAllElements();
        table.getSelectionModel().clearSelection();
        tableInitRow(resultSet);
        table.updateUI();
    }

    public void setBookTypeComboBox() {
        Enumeration<String> keys = bundle.getKeys();
        bookTypeCombo1.addItem("");
        while (keys.hasMoreElements()) {
            String item = keys.nextElement();
            bookTypeCombo1.addItem(item + "  " + bundle.getString(item));
        }
    }

    public void updateTableValues(int row,int leftAmount) {
        model.setValueAt(leftAmount-1,row,7);
    }

    public void borrowBook(int row) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String bookNo = (String) model.getValueAt(row,0);
        int leftAmount = (Integer) table.getValueAt(row,7);

        int option = JOptionPane.showOptionDialog(new JFrame(),"是否确定借阅该图书？","系统提示",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);

        if (option == JOptionPane.OK_OPTION) {
            if (reader.borrowBooks(bookNo,sdf.format(date))) {
                updateTableValues(row,leftAmount);
                JOptionPane.showMessageDialog(new JFrame(),"借阅图书成功！\n请在30天内归还或续借！");
            } else {
                JOptionPane.showMessageDialog(new JFrame(),"借阅图书失败！\n您已租借该图书,您可以直接选择续借！");
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame(),"取消借阅成功");
        }
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }
}

