package ui;

import tools.BooksInfoManagementTools;
import tools.ImageInput;
import tools.InputTools;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.Enumeration;
import java.util.ResourceBundle;


public class AddBookPage extends JPanel {
    //创建输入框
    JTextField bookName;
    JTextField bookAuthor;
    JTextField publisher;
    JTextField publishTime;
    JComboBox<String> bookTypeComboBox;
    JTextField sumAmount;
    JTextArea bookDescField;

    //创建按钮
    JButton add;
    JButton reset;
    JPanel contentPane = new JPanel();
    ResourceBundle bundle = ResourceBundle.getBundle("bookType");

    public AddBookPage() {
        setSize(700, 500);
        setLayout(new BorderLayout());
        add(contentPane);


        controlInit();

        listeners();

        textFieldInit();
        setBookTypeComboBox();
    }

    public void listeners() {
        add.addActionListener(e -> {
            String name = bookName.getText();
            String author = bookAuthor.getText();
            String bookPublisher = publisher.getText();
            String bookPublishTime = publishTime.getText();
            String bookType = (String) bookTypeComboBox.getSelectedItem();
            String bookAmount = sumAmount.getText();
            if("".equals(author) ||
                    "".equals(name) ||
                    "".equals(bookPublisher) ||
                    "".equals(bookPublishTime) ||
                    "".equals(bookAmount)) {
                JOptionPane.showMessageDialog(new JFrame(), "选项不能为空！");
                return;
            }
            if (InputTools.judgeStringFormat(bookPublishTime)) {
                JOptionPane.showMessageDialog(new JFrame(),"日期格式不对！\n请输入形如  2021-12");
                return;
            }
            int[] bookCount = InputTools.judgeStringToInt(bookAmount,"图书库存只能为整数！");
            if (bookCount[1] == 1) {
                assert bookType != null;
                if (BooksInfoManagementTools.addBooks(name,author,bookPublisher,bookPublishTime,bookCount[0],bookCount[0],bookType)) {
                    resetInput();
                    bookTypeComboBox.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(new JFrame(),"图书添加成功！");
                } else {
                    JOptionPane.showMessageDialog(new JFrame(),"图书添加失败！");
                }
            }
        });

        reset.addActionListener(e -> resetInput());

        publishTime.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(publishTime.getText().equals("例如：2021-12")){
                    publishTime.setText("");
                    publishTime.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(publishTime.getText().length()<1){
                    publishTime.setText("例如：2021-12");
                    publishTime.setForeground(new Color(0x7E7E80));
                }
            }
        });
    }


    /**
     * 重置输入框内容
     */
    public void resetInput() {
        bookName.setText("");
        bookAuthor.setText("");
        publisher.setText("");
        publishTime.setText("");
        sumAmount.setText("");
        bookDescField.setText("");
    }

    public void textFieldInit() {
        bookName.setBorder(new LineBorder(new Color(0x7DC3F1),1,true));
        bookAuthor.setBorder(new LineBorder(new Color(0x7DC3F1),1,true));
        publisher.setBorder(new LineBorder(new Color(0x7DC3F1),1,true));
        publishTime.setBorder(new LineBorder(new Color(0x7DC3F1),1,true));
        sumAmount.setBorder(new LineBorder(new Color(0x7DC3F1),1,true));
        bookDescField.setBorder(new LineBorder(new Color(0x7DC3F1),1,true));
    }

    public void setBookTypeComboBox() {
        Enumeration<String> keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            String item = keys.nextElement();
            bookTypeComboBox.addItem(item + "  " + bundle.getString(item));
        }
    }

    public void controlInit() {
        contentPane.setSize(700,500);
        contentPane.setLocation(0,0);
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        contentPane.setBorder(new EmptyBorder(0, 30, 10, 30));

        JPanel titlePanel = new JPanel();
        contentPane.add(titlePanel);

        JLabel labelAddBook = new JLabel("图书添加");
        labelAddBook.setIcon(new ImageIcon(new ImageInput(new File("src/images/ADD.png")).getImage()));
        labelAddBook.setFont(new Font("微软雅黑", Font.BOLD, 20));
        titlePanel.setPreferredSize(new Dimension(this.getWidth(), 70));
        titlePanel.add(labelAddBook);


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        mainPanel.setPreferredSize(new Dimension(getWidth(), 280));
        contentPane.add(mainPanel,BorderLayout.CENTER);

        JLabel labelBookId = new JLabel("图书名称：");
        labelBookId.setIcon(new ImageIcon(new ImageInput(new File("src/images/book-name-icon.png")).getImage()));
        mainPanel.add(labelBookId);

        bookName = new JTextField();
        bookName.setPreferredSize(new Dimension(150, 30));
        mainPanel.add(bookName);


        JLabel labelBookName = new JLabel("图书作者：");
        labelBookName.setIcon(new ImageIcon(new ImageInput(new File("src/images/book-author-icon.png")).getImage()));
        mainPanel.add(labelBookName);

        bookAuthor = new JTextField();
        bookAuthor.setPreferredSize(new Dimension(150, 30));
        mainPanel.add(bookAuthor);


        JLabel labelAuthor = new JLabel("    出版社：");
        labelAuthor.setIcon(new ImageIcon(new ImageInput(new File("src/images/publisher.png")).getImage()));
        mainPanel.add(labelAuthor);

        publisher = new JTextField();
        publisher.setPreferredSize(new Dimension(150, 30));
        mainPanel.add(publisher);


        JLabel labelPrice = new JLabel("出版时间：");
        labelPrice.setIcon(new ImageIcon(new ImageInput(new File("src/images/publishTime.png")).getImage()));
        mainPanel.add(labelPrice);

        publishTime = new JTextField();
        publishTime.setPreferredSize(new Dimension(150, 30));
        publishTime.setText("例如：2021-12");
        publishTime.setForeground(new Color(0x7E7E80));
        mainPanel.add(publishTime);


        JLabel labelBookType = new JLabel("图书类别：");
        labelBookType.setIcon(new ImageIcon(new ImageInput(new File("src/images/bookTypeAddmenu-icon.png")).getImage()));
        mainPanel.add(labelBookType);
        bookTypeComboBox = new JComboBox<>();
        bookTypeComboBox.setPreferredSize(new Dimension(150, 30));
        mainPanel.add(bookTypeComboBox);


        JLabel labelBookCount = new JLabel("图书库存：");
        labelBookCount.setIcon(new ImageIcon(new ImageInput(new File("src/images/sumAmount.png")).getImage()));
        mainPanel.add(labelBookCount);

        sumAmount = new JTextField();
        sumAmount.setPreferredSize(new Dimension(150, 30));
        mainPanel.add(sumAmount);


        JLabel labelBookDesc = new JLabel("图书描述：");
        labelBookDesc.setIcon(new ImageIcon(new ImageInput(new File("src/images/bookTypeDesc-icon.png")).getImage()));
        mainPanel.add(labelBookDesc);

        bookDescField = new JTextArea();
        bookDescField.setPreferredSize(new Dimension(450, 80));
        bookDescField.setLineWrap(true);
        mainPanel.add(bookDescField);



        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,40,0));
        contentPane.add(buttonPanel);


        add = new JButton("添加");
        add.setFocusPainted(false);
        add.setPreferredSize(new Dimension(60,30));
        add.setBorder(new LineBorder(new Color(0x7DC3F1),1,true));
//        add.setIcon(new ImageIcon(new ImageInput(new File("src/images/add-icon.png")).getImage()));
        buttonPanel.add(add);


        reset = new JButton("重置");
        reset.setFocusPainted(false);
        reset.setPreferredSize(new Dimension(60,30));
        reset.setBorder(new LineBorder(new Color(0x7DC3F1),1,true));
//        reset.setIcon(new ImageIcon(new ImageInput(new File("src/images/reset.png")).getImage()));
        buttonPanel.add(reset);
    }
}
