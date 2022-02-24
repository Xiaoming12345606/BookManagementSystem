package ui;

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
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.Vector;

public class MyBooksPage extends JPanel {
    private Reader reader;
    JPanel head = new JPanel();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();

    JLabel bookName = new JLabel("图书名称：");
    JLabel editBookName = new JLabel("图书名称：");
    JLabel editPublisher = new JLabel("   出版社：");
    JLabel editPublishTime = new JLabel("出版时间：");
    JLabel editBookAmount = new JLabel("图书库存：");
    JLabel bookType = new JLabel("图书种类：");
    JLabel headLabel = new JLabel("我的图书");
    JComboBox<String> bookTypeCombo1 = new JComboBox<>();

    JTextField bookNameField = new JTextField();
    JTextField editBookNameField = new JTextField();
    JTextField editPublisherField = new JTextField();
    JTextField editBookAmountField = new JTextField();
    JTextField editPublishTimeField = new JTextField();

    JButton returnBook = new JButton("归还");
    JButton renew = new JButton("续借");
    JButton search = new JButton("搜索");
    JButton view = new JButton("显示所有");

    JTable table = new JTable();
    JScrollPane scrollPane = new JScrollPane(table);
    DefaultTableModel model = new DefaultTableModel();
    ResourceBundle bundle = ResourceBundle.getBundle("bookType");

    public MyBooksPage() {
        this.setPreferredSize(new Dimension(1000, 560));
        controlInit();
        listeners();
        setBookTypeComboBox();

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
        model.addColumn("是否续借");

        scrollPane.setPreferredSize(new Dimension(800, 300));
        table.setModel(model);
        tableStyle();
        panel2.add(scrollPane, BorderLayout.CENTER);
        panel2.setBorder(new LineBorder(new Color(0x7DC3F1),1,true));

        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
        panel3.setPreferredSize(new Dimension(600, 140));
        editBookNameField.setPreferredSize(new Dimension(150, 30));
        editPublisherField.setPreferredSize(new Dimension(150, 30));
        editBookAmountField.setPreferredSize(new Dimension(150, 30));
        editPublishTimeField.setPreferredSize(new Dimension(150, 30));

        editBookNameField.setBorder(new LineBorder(new Color(0x7DC3F1), 1, true));
        editPublisherField.setBorder(new LineBorder(new Color(0x7DC3F1), 1, true));
        editPublishTimeField.setBorder(new LineBorder(new Color(0x7DC3F1), 1, true));
        editBookAmountField.setBorder(new LineBorder(new Color(0x7DC3F1), 1, true));

        panel3.add(editBookName);
        panel3.add(editBookNameField);
        panel3.add(editPublisher);
        panel3.add(editPublisherField);
        panel3.add(editPublishTime);
        panel3.add(editPublishTimeField);
        panel3.add(editBookAmount);
        panel3.add(editBookAmountField);

        returnBook.setFocusPainted(false);
        renew.setFocusPainted(false);
        returnBook.setPreferredSize(new Dimension(60,30));
        renew.setPreferredSize(new Dimension(60,30));
        returnBook.setBorder(new LineBorder(new Color(0x7DC3F1), 1, true));
        renew.setBorder(new LineBorder(new Color(0x7DC3F1), 1, true));
        panel4.setLayout(new FlowLayout(FlowLayout.CENTER,100,30));
        panel4.setPreferredSize(new Dimension(1000,100));
        panel4.add(returnBook);
        panel4.add(renew);
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

        returnBook.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                returnBook(row);
            }
        });

        renew.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                tableInitRenew(row);
            }
        });


    }

    public void tableInit() {
        ResultSet resultSet = reader.selectBooksForOwn();
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
            resultSet = reader.selectBooksForOwnByType(bundle.getString(bookType));
        } else if ("".equals(bookType)) {
            resultSet = reader.selectBooksForOwn("%"+ bookName + "%");
        }else {
            resultSet = reader.selectBooksForOwn("%" + bookName + "%",bundle.getString(bookType));
        }
        tableInitRow(resultSet);
    }

    public void tableInitRenew(int row) {
        String bookNo = (String) model.getValueAt(row,0);
        int option = JOptionPane.showOptionDialog(new JFrame(),"是否确定续借该图书？","系统提示",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
        if (option == JOptionPane.OK_OPTION) {
            if (reader.renewBook(bookNo)) {
                model.setValueAt("是",row,6);
                JOptionPane.showMessageDialog(new JFrame(),"续借成功！\n您的借阅时间将延长30天！");
            } else {
                JOptionPane.showMessageDialog(new JFrame(),"续借失败！");
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame(),"您已取消续借！");
        }

    }

    public void tableInitRow(ResultSet resultSet) {

        try {
            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                for (int i = 1;i <= 7;i ++) {
                    vector.add(resultSet.getString(i));
                }

                model.addRow(vector);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setBookTypeComboBox() {
        Enumeration<String> keys = bundle.getKeys();
        bookTypeCombo1.addItem("");
        while (keys.hasMoreElements()) {
            String item = keys.nextElement();
            bookTypeCombo1.addItem(item + "  " + bundle.getString(item));
        }
    }

    public void updateTableRow(int row) {
        model.removeRow(row);
    }

    public void returnBook(int row) {
        String bookNo = (String) model.getValueAt(row,0);
        int option = JOptionPane.showOptionDialog(new JFrame(),"是否确定归还该图书？","系统提示",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);

        if (option == JOptionPane.OK_OPTION) {
            if (reader.returnBooks(bookNo)) {
                updateTableRow(row);
                JOptionPane.showMessageDialog(new JFrame(),"归还图书成功！");
            } else {
                JOptionPane.showMessageDialog(new JFrame(),"归还图书失败！\n您已归还该图书！");
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame(),"取消归还成功！");
        }
    }
    public void setReader(Reader reader) {
        this.reader = reader;
    }
}

