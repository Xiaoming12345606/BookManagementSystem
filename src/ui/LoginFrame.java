package ui;

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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginFrame extends JFrame {
    public UserInfo userInfo = new UserInfo();
    JLabel title = new JLabel("BookManagementSystem");
    JLabel username = new JLabel("账    号：");
    JLabel password = new JLabel(" 密    码：");
    JLabel logon = new JLabel("没有账号？注册一个");
    JLabel checkLabel = new JLabel("登陆角色：");
    JTextField accountNoField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton login = new JButton("登陆");
    JButton reset = new JButton("重置");
    JPanel root = new JPanel();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();
    JPanel checkPanel = new JPanel();

    JRadioButton admin = new JRadioButton("管理员");
    JRadioButton reader = new JRadioButton("用户");
    ButtonGroup buttonGroup = new ButtonGroup();
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    ResourceBundle bundle = ResourceBundle.getBundle("administrator");
    public LoginFrame() throws HeadlessException {
        setSize(700,500);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(dimension.width/2-350,dimension.height/2-250);
        setContentPane(root);
        setIconImage(new ImageInput(new File("src/images/bookIconMax.png")).getImage());
        controlInit();
        listeners();

        root.add(panel1);
        root.add(panel2);
        root.add(checkPanel);
        root.add(panel3);
        root.add(panel4);
    }

    public void controlInit() {
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER,0,50));
        panel1.setPreferredSize(new Dimension(1000,120));
        title.setFont(new Font("microsoft YaHei",Font.BOLD,26));
        title.setIcon(new ImageIcon(new ImageInput(new File("src/images/book.png")).getImage()));
        panel1.add(title);

        panel2.setLayout(new FlowLayout(FlowLayout.CENTER,30,30));
        panel2.setPreferredSize(new Dimension(400,160));
        username.setFont(new Font("microsoft YaHei",Font.BOLD,20));
        password.setFont(new Font("microsoft YaHei",Font.BOLD,20));

        username.setIcon(new ImageIcon(new ImageInput(new File("src/images/user.png")).getImage()));
        password.setIcon(new ImageIcon(new ImageInput(new File("src/images/password.png")).getImage()));

        accountNoField.setPreferredSize(new Dimension(150,40));
        passwordField.setPreferredSize(new Dimension(150,40));
        accountNoField.setBorder(new LineBorder(new Color(0x7DC3F1),1,true));
        passwordField.setBorder(new LineBorder(new Color(0x7DC3F1),1,true));
        panel2.add(username);
        panel2.add(accountNoField);
        panel2.add(password);
        panel2.add(passwordField);

        panel3.setLayout(new FlowLayout(FlowLayout.CENTER,80,10));
        panel3.setPreferredSize(new Dimension(700,60));
        login.setFocusPainted(false);
        reset.setFocusPainted(false);
        login.setPreferredSize(new Dimension(74,34));
        reset.setPreferredSize(new Dimension(74,34));
        login.setIcon(new ImageIcon(new ImageInput(new File("src/images/login.png")).getImage()));
        reset.setIcon(new ImageIcon(new ImageInput(new File("src/images/reset.png")).getImage()));
        login.setBorder(new LineBorder(new Color(0x7DC3F1),1,true));
        reset.setBorder(new LineBorder(new Color(0x7DC3F1),1,true));
        panel3.add(login);
        panel3.add(reset);

        logon.setFont(new Font("microsoft YaHei",Font.PLAIN,14));
        logon.setForeground(new Color(0x316FF3));
        panel4.add(logon);

        checkPanel.setLayout(new FlowLayout(FlowLayout.CENTER,40,30));
        checkPanel.setPreferredSize(new Dimension(1000,60));

        admin.setFocusPainted(false);
        reader.setFocusPainted(false);
        admin.setSelected(true);
        admin.setFont(new Font("microsoft YaHei",Font.PLAIN,14));
        reader.setFont(new Font("microsoft YaHei",Font.PLAIN,14));
        checkLabel.setFont(new Font("microsoft YaHei",Font.PLAIN,14));
        buttonGroup.add(admin);
        buttonGroup.add(reader);
        checkPanel.add(checkLabel);
        checkPanel.add(admin);
        checkPanel.add(reader);
    }

    public void listeners() {
        logon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                PageSwitch.logonFrame.accountLabel.setText(RandomUtil.getStr());
                PageSwitch.logonFrame.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                logon.setForeground(new Color(0x626264));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                logon.setForeground(new Color(0x316FF3));
            }
        });

        reset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                accountNoField.setText("");
                passwordField.setText("");
            }
        });

        login.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String username = accountNoField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                judgeUsers(username,password);
                admin.setSelected(true);
            }
        });

        accountNoField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String username = accountNoField.getText().trim();
                    String password = new String(passwordField.getPassword()).trim();
                    judgeUsers(username,password);
                    admin.setSelected(true);
                }
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String username = accountNoField.getText().trim();
                    String password = new String(passwordField.getPassword()).trim();
                    judgeUsers(username,password);
                    admin.setSelected(true);
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                DatabaseConnection.disconnection(DbConnection.dbConnection);
                System.exit(0);
            }
        });
    }

    public void windowSwitch(User user) {
        updateData();
        PageSwitch.loginFrame.setVisible(false);
        PageSwitch.mainFrame.setUser(user);
        PageSwitch.mainFrame.setVisible(true);
    }

    public void judgeUsers(String username, String password) {
        if (admin.isSelected()) {
            if (!bundle.getString("accountNo").equals(username)) {
                JOptionPane.showMessageDialog(new JFrame(),"没有此用户","登陆提示",JOptionPane.INFORMATION_MESSAGE);
            } else if (!bundle.getString("password").equals(password)) {
                JOptionPane.showMessageDialog(new JFrame(),"密码错误","登陆提示",JOptionPane.INFORMATION_MESSAGE);
            } else if (username.equals(bundle.getString("accountNo")) && password.equals(bundle.getString("password"))) {
                windowSwitch(new Administrator(username,bundle.getString("username"),password));
//                JOptionPane.showMessageDialog(new JFrame(),"登陆成功","登陆提示",JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            login(username,password);
        }
    }

    public void login(String username,String password) {
        LoginInfo loginInfo = userInfo.login(username,password);
        if (loginInfo.equals(LoginInfo.SUCCESS)) {
            ResultSet resultSet = userInfo.getUsername(username);
            try {
                resultSet.next();
                windowSwitch(new Reader(username,resultSet.getString("name"),password));
            } catch (SQLException e) {
                e.printStackTrace();
            }
//            JOptionPane.showMessageDialog(new JFrame(),"登陆成功","登陆提示",JOptionPane.INFORMATION_MESSAGE);
        } else if (loginInfo.equals(LoginInfo.NULL_USER)) {
            JOptionPane.showMessageDialog(new JFrame(),"没有此用户","登陆提示",JOptionPane.INFORMATION_MESSAGE);
        } else if (loginInfo.equals(LoginInfo.ERR_PWD)) {
            JOptionPane.showMessageDialog(new JFrame(),"密码错误","登陆提示",JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(new JFrame(),"登陆失败","登陆提示",JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void updateData() {
        accountNoField.setText("");
        passwordField.setText("");
    }
}
