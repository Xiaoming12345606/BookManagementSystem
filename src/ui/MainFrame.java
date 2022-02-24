package ui;

import thread.TimeOutThread;
import tools.*;
import user.Administrator;
import user.Reader;
import user.User;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.ResultSet;

public class MainFrame extends JFrame {
    private User user;
    private final int width = 1000;
    private final AccountInfoPage accountInfoPage = new AccountInfoPage();
    public EditBookPage editBookPage = new EditBookPage();
    public BorrowBookPage borrowBookPage = new BorrowBookPage();
    private final AddBookPage addBookPage = new AddBookPage();
    public MyBooksPage myBooksPage = new MyBooksPage();

    JLabel addBook = new JLabel("新增图书");
    JLabel bookInfo = new JLabel("图书信息");
    JLabel accountInfo = new JLabel("账户信息");
    JLabel logout = new JLabel("退出登陆");
    JLabel bookInfoForReader = new JLabel("图书信息");
    JLabel myBooks = new JLabel("我的图书");
    JPanel root = new JPanel();
    JPanel labelPane = new JPanel();
    JPanel labelPane1 = new JPanel();
    JPanel labelPane2 = new JPanel();
    JPanel displayPane = new JPanel();
    JTextField textField = new JTextField(12);
    JButton search = new JButton("搜索");
    JButton refresh = new JButton("刷新");
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    CardLayout cardLayout = new CardLayout();
    TimeOutThread timeOutThread = new TimeOutThread();

    public MainFrame() throws HeadlessException {
        this.setResizable(false);
        this.setContentPane(root);
        int height = 600;
        this.setSize(width, height);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocation((dimension.width-width)/2,(dimension.height- height)/2);
        this.setIconImage(new ImageInput(new File("src/images/icon.png")).getImage());
        controlInit();
        listeners();
        addPages();
        timeOutThread.start();
    }

    public void labelStyle(JLabel label) {
        label.setOpaque(true);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(new Color(0x8A8A8D));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setForeground(new Color(0));
            }
        });
    }

    public void labelChosenListener(JLabel label,String page) {
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(displayPane,page);
                labelPane.updateUI();
            }
        });
    }

    public void controlInit() {
        root.setLayout(null);
        labelPane.setSize(width+4,40);
        labelPane.setLocation(-2,-2);
        labelPane1.setSize(width-300,28);
        labelPane1.setLocation(0,10);
        labelPane2.setSize(300,28);
        labelPane2.setLocation(width-300,10);
        displayPane.setSize(width+4,772);
        displayPane.setLocation(0,38);/////
        addBook.setFont(new Font("microsoft YaHei",Font.BOLD,14));
        bookInfo.setFont(new Font("microsoft YaHei",Font.BOLD,14));
        accountInfo.setFont(new Font("microsoft YaHei",Font.BOLD,14));
        logout.setFont(new Font("microsoft YaHei",Font.BOLD,14));
        bookInfoForReader.setFont(new Font("microsoft YaHei",Font.BOLD,14));
        myBooks.setFont(new Font("microsoft YaHei",Font.BOLD,14));
        labelPane.setLayout(null);
        displayPane.setLayout(cardLayout);
        labelPane1.setLayout(new FlowLayout(FlowLayout.LEFT,30,0));
        labelPane2.setLayout(new FlowLayout(FlowLayout.RIGHT,24,0));
        addBook.setSize(80,this.getHeight());
        bookInfo.setSize(80,this.getHeight());
        accountInfo.setSize(80,this.getHeight());
        logout.setSize(80,this.getHeight());
        bookInfoForReader.setSize(80,this.getHeight());

        labelStyle(addBook);
        labelStyle(bookInfo);
        labelStyle(accountInfo);
        labelStyle(logout);
        labelStyle(bookInfoForReader);
        labelStyle(myBooks);

        textField.setPreferredSize(new Dimension(textField.getWidth(),20));
        textField.setBorder(new LineBorder(new Color(0x7DC3F1),1,true));
        textField.setFont(new Font("microsoft YaHei",Font.PLAIN,12));
        search.setFocusPainted(false);
        search.setFont(new Font("microsoft YaHei",Font.PLAIN,12));
        search.setPreferredSize(new Dimension(40,20));
        search.setBorder(new LineBorder(new Color(0x7DC3F1),1,true));

        refresh.setPreferredSize(new Dimension(40,20));
        refresh.setBorder(new LineBorder(new Color(0x7DC3F1),1,true));
        refresh.setFont(new Font("microsoft YaHei",Font.PLAIN,12));
        refresh.setFocusPainted(false);

        labelPane2.add(textField);
        labelPane2.add(search);
        labelPane2.add(refresh);

        labelPane.add(labelPane1);
        labelPane.add(labelPane2);
        labelPane.setBorder(new LineBorder(new Color(0xA8A3A3),2));
        root.add(labelPane);
        root.add(displayPane);
    }

    public void addPages() {
        displayPane.add(addBookPage,"addBookPage");
        displayPane.add(editBookPage,"editBookPage");
        displayPane.add(accountInfoPage,"accountInfoPage");
        displayPane.add(borrowBookPage,"bookInfoForReader");
        displayPane.add(myBooksPage,"myBooks");
    }

    public void listeners() {
//        buttonListener(search,textField);
        labelChosenListener(addBook,"addBookPage");
        labelChosenListener(bookInfo,"editBookPage");
        labelChosenListener(accountInfo,"accountInfoPage");
        labelChosenListener(bookInfoForReader,"bookInfoForReader");
        labelChosenListener(myBooks,"myBooks");
        logout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                setUser(null);
                PageSwitch.loginFrame.setVisible(true);
            }
        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                judgeUsers();
                accountInfoPage.account.setText(user.getAccountNo());
                accountInfoPage.username.setText(user.getUserName());
                accountInfoPage.password.setText(user.getPassword());
                accountInfoPage.setOldPassword(user.getPassword());
                timeOutThread.setTime2(System.currentTimeMillis());
            }
        });

        addWindowStateListener(e -> {
            int newState = e.getNewState();
            if (newState == JFrame.ICONIFIED) {
                timeOutThread.setFlag(true);
                timeOutThread.setTime2(System.currentTimeMillis());
            } else if (newState == JFrame.NORMAL) {
                timeOutThread.setFlag(false);
            }
        });

        bookInfo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                editBookPage.tableInit();
            }
        });

        bookInfoForReader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                borrowBookPage.tableInit();
            }
        });

        myBooks.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                myBooksPage.tableInit();
            }
        });

        search.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String text = textField.getText();
                search(text);
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                DatabaseConnection.disconnection(DbConnection.dbConnection);
                System.exit(0);
            }
        });

        refresh.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DatabaseConnection databaseConnection = new DatabaseConnection();
                databaseConnection.getConnection();
                DbConnection.dbConnection = databaseConnection.connection;
                PageSwitch.loginFrame.userInfo.setConnection(DbConnection.dbConnection);
                user.setConnection(DbConnection.dbConnection);
                BooksInfoManagementTools.setConnection(DbConnection.dbConnection);
                editBookPage.tableInit();
                borrowBookPage.tableInit();
                myBooksPage.tableInit();
            }
        });
    }

    public void judgeUsers() {
        labelPane1.removeAll();
        if (user instanceof Administrator) {
            labelPane1.add(addBook);
            labelPane1.add(bookInfo);
            labelPane1.add(accountInfo);
            cardLayout.show(displayPane,"addBookPage");
        } else if (user instanceof Reader){
            labelPane1.add(bookInfoForReader);
            labelPane1.add(myBooks);
            labelPane1.add(accountInfo);
            borrowBookPage.setReader((Reader) user);
            myBooksPage.setReader((Reader) user);
            cardLayout.show(displayPane,"bookInfoForReader");
        }
        accountInfoPage.user = user;
        labelPane1.add(logout);
        labelPane1.updateUI();
    }

    public void search(String text) {
        if (!"".equals(text)) {
            ResultSet resultSet = BooksInfoManagementTools.titleSelectBooks(text);
            if (user instanceof Administrator) {
                cardLayout.show(displayPane,"editBookPage");
                editBookPage.tableInitRowBySearch(resultSet);

            } else {
                cardLayout.show(displayPane,"bookInfoForReader");
                borrowBookPage.tableInitRowBySearch(resultSet);
            }
            textField.setText("");
            borrowBookPage.bookName.setText("");
            borrowBookPage.bookTypeCombo1.setSelectedIndex(0);
            editBookPage.bookName.setText("");
            editBookPage.bookTypeCombo1.setSelectedIndex(0);
            editBookPage.clearTextField();
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
