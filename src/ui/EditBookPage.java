package ui;

import tools.BooksInfoManagementTools;
import tools.ImageInput;
import tools.InputTools;
import tools.TableInfoTools;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.Vector;

public class EditBookPage extends JPanel {
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
    JLabel headLabel = new JLabel("图书信息");
    JComboBox<String> bookTypeCombo1 = new JComboBox<>();

    JTextField bookNameField = new JTextField();
    JTextField editBookNameField = new JTextField();
    JTextField editPublisherField = new JTextField();
    JTextField editBookAmountField = new JTextField();
    JTextField editPublishTimeField = new JTextField();

    JButton modify = new JButton("修改");
    JButton delete = new JButton("删除");
    JButton search = new JButton("搜索");
    JButton view = new JButton("显示所有");

    JTable table = new JTable();
    JScrollPane scrollPane = new JScrollPane(table);
    DefaultTableModel model = new DefaultTableModel();

    ResourceBundle bundle = ResourceBundle.getBundle("bookType");

    public EditBookPage() {
        this.setPreferredSize(new Dimension(1000, 560));
        controlInit();
        listeners();
        tableInit();
        setBookTypeComboBox();
        this.add(head);
        this.add(panel1);
        this.add(panel2);
        this.add(panel3);
        this.add(panel4);
    }

    public void controlInit() {
        head.setPreferredSize(new Dimension(1000,40));
        headLabel.setFont(new Font("microsoft YaHei",Font.BOLD,20));
        headLabel.setIcon(new ImageIcon(new ImageInput(new File("src/images/bookInfo.png")).getImage()));
        head.add(headLabel);
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 16));
//        panel1.setBorder(new LineBorder(new Color(0x8F8E8E),1,true));
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
        scrollPane.setPreferredSize(new Dimension(900, 200));
        table.setModel(model);

        tableStyle();

        panel2.add(scrollPane,BorderLayout.CENTER);
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

        modify.setFocusPainted(false);
        delete.setFocusPainted(false);
        modify.setPreferredSize(new Dimension(60,30));
        delete.setPreferredSize(new Dimension(60,30));
        modify.setIcon(new PageIcon(new File("src/images/modify-max-icon.png"),0,5,20,20));
        delete.setIcon(new PageIcon(new File("src/images/bookDelect-icon.png"),0,5,20,20));
        modify.setBorder(new LineBorder(new Color(0x7DC3F1), 1, true));
        delete.setBorder(new LineBorder(new Color(0x7DC3F1), 1, true));
        panel4.setLayout(new FlowLayout(FlowLayout.CENTER,100,0));
        panel4.setPreferredSize(new Dimension(1000,100));
        panel4.add(modify);
        panel4.add(delete);
    }

    public void listeners() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                String[] values = TableInfoTools.getTableValues(row,8,model);
                updateFieldData(values[2],values[4],values[5],values[6]);
            }
        });

        modify.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                String no = (String) model.getValueAt(row,0);
                String bookName = editBookNameField.getText();
                String bookPublisher = editPublisherField.getText();
                String publishTime = editPublishTimeField.getText();
                String bookAmount = editBookAmountField.getText();
                if("".equals(no) ||
                        "".equals(bookName) ||
                        "".equals(bookPublisher) ||
                        "".equals(publishTime) ||
                        "".equals(bookAmount)) {
                    JOptionPane.showMessageDialog(new JFrame(), "选项不能为空！");
                    return;
                }
                if (InputTools.judgeStringFormat(publishTime)) {
                    JOptionPane.showMessageDialog(new JFrame(),"日期格式不对！\n请输入形如  2021-12");
                    return;
                }
                boolean flag = updateTableValues(no,bookName,bookPublisher,publishTime,bookAmount);
                if (flag) {
                    TableInfoTools.setRowValues(row,model,new String[]{no,null,bookName,null,bookPublisher,publishTime,bookAmount,null});
                    JOptionPane.showMessageDialog(new JFrame(),"修改图书信息成功！");
                } else {
                    JOptionPane.showMessageDialog(new JFrame(),"修改图书信息失败！");
                }

            }
        });

        delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                String[] values = TableInfoTools.getTableValues(row,8,model);
                int option = JOptionPane.showOptionDialog(new JFrame(),"确定删除该图书？","系统提示",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                }
                boolean flag = deleteBooks(values[0]);
                if (flag) {
                    model.removeRow(row);
                    JOptionPane.showMessageDialog(new JFrame(),"删除图书成功！");
                } else {
                    JOptionPane.showMessageDialog(new JFrame(),"删除图书失败！");
                }
            }
        });

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
                clearTextField();
            }
        });

        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableInit();
                clearTextField();
            }
        });

    }


    public void updateFieldData(String bookName,String publisher,String publishTime,String sumAmount) {
        String subPublishTime = publishTime.substring(0,publishTime.length()-1);
        String[] publishTimes = subPublishTime.split("年");
        editBookNameField.setText(bookName);
        editPublisherField.setText(publisher);
        editPublishTimeField.setText(publishTimes[0] + '-' + publishTimes[1]);
        editBookAmountField.setText(sumAmount);
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
        clearTextField();
    }

    public void tableInitRow(ResultSet resultSet) {

        try {
            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString(1));
                vector.add(resultSet.getString(8));
                vector.add(resultSet.getString(2));
                vector.add(resultSet.getString(3));
                vector.add(resultSet.getString(4));
                vector.add(resultSet.getString(5));
                vector.add(resultSet.getString(6));
                vector.add(resultSet.getString(7));

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

    public boolean updateTableValues(String bookNo,String bookName,String publisher,String publishTime,String sumAmount) {
        int[] values = InputTools.judgeStringToInt(sumAmount,"图书库存只能为整数！");
        if (values[1] == 1) {
            return BooksInfoManagementTools.modifyBooks(bookNo, bookName, publisher, publishTime, values[0]);
        }
        return false;
    }

    public void clearTextField() {
        editBookNameField.setText("");
        editPublisherField.setText("");
        editPublishTimeField.setText("");
        editBookAmountField.setText("");
    }

    public boolean deleteBooks(String bookNo) {
        return BooksInfoManagementTools.deleteBooks(bookNo);

    }


}

