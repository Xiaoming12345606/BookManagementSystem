package ui;

import tools.ImageInput;
import tools.PageSwitch;
import tools.UserInfo;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class LogonFrame extends JFrame {
    UserInfo userInfo = PageSwitch.loginFrame.userInfo;
    JLabel title = new JLabel("BookManagementSystem");
    JLabel accountNo = new JLabel("账      号：");
    JLabel username = new JLabel("用 户 名：");
    JLabel password = new JLabel(" 密      码：");
    JLabel determinePassword = new JLabel("确认密码：");
    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JPasswordField determinePasswordField = new JPasswordField();
    JLabel accountLabel = new JLabel();
    JButton logon = new JButton("注册");
    JButton reset = new JButton("重置");
    JPanel root = new JPanel();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    public LogonFrame() throws HeadlessException {
        setSize(700,500);
        setResizable(false);
        setLocation(dimension.width/2-350,dimension.height/2-250);
        setContentPane(root);
        setIconImage(new ImageInput(new File("src/images/bookIconMax.png")).getImage());
        controlInit();
        listeners();

        root.add(panel1);
        root.add(panel2);
        root.add(panel3);
//        setVisible(true);
    }

    public void controlInit() {
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER,0,20));
        title.setFont(new Font("microsoft YaHei",Font.BOLD,24));
        title.setIcon(new ImageIcon(new ImageInput(new File("src/images/book.png")).getImage()));
        panel1.add(title);

        panel2.setLayout(new FlowLayout(FlowLayout.CENTER,30,20));
        panel2.setPreferredSize(new Dimension(400,270));
        username.setFont(new Font("microsoft YaHei",Font.BOLD,20));
        password.setFont(new Font("microsoft YaHei",Font.BOLD,20));
        determinePassword.setFont(new Font("microsoft YaHei",Font.BOLD,20));
        accountNo.setFont(new Font("microsoft YaHei",Font.BOLD,20));
        username.setIcon(new ImageIcon(new ImageInput(new File("src/images/user.png")).getImage()));
        password.setIcon(new ImageIcon(new ImageInput(new File("src/images/password.png")).getImage()));
        determinePassword.setIcon(new ImageIcon(new ImageInput(new File("src/images/password.png")).getImage()));
        accountNo.setIcon(new ImageIcon(new ImageInput(new File("src/images/account.png")).getImage()));

        usernameField.setPreferredSize(new Dimension(150,40));
        passwordField.setPreferredSize(new Dimension(150,40));
        determinePasswordField.setPreferredSize(new Dimension(150,40));
        accountLabel.setPreferredSize(new Dimension(150,40));
        usernameField.setBorder(new LineBorder(new Color(0x7DC3F1),1,true));
        passwordField.setBorder(new LineBorder(new Color(0x7DC3F1),1,true));
        determinePasswordField.setBorder(new LineBorder(new Color(0x7DC3F1),1,true));
        accountLabel.setBorder(new EmptyBorder(0,0,0,0));

        accountLabel.setFont(new Font("microsoft YaHei",Font.BOLD,18));
        accountLabel.setForeground(new Color(0x232323));
        accountLabel.setBackground(new Color(238,238,238));
        panel2.add(accountNo);
        panel2.add(accountLabel);
        panel2.add(username);
        panel2.add(usernameField);
        panel2.add(password);
        panel2.add(passwordField);
        panel2.add(determinePassword);
        panel2.add(determinePasswordField);

        panel3.setLayout(new FlowLayout(FlowLayout.CENTER,80,20));
        panel3.setPreferredSize(new Dimension(700,60));
        logon.setFocusPainted(false);
        reset.setFocusPainted(false);
        logon.setPreferredSize(new Dimension(74,34));
        reset.setPreferredSize(new Dimension(74,34));
        logon.setBorder(new LineBorder(new Color(0x7DC3F1),1,true));
        reset.setBorder(new LineBorder(new Color(0x7DC3F1),1,true));
        panel3.add(logon);
        panel3.add(reset);
    }

    public void listeners() {
        reset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                usernameField.setText("");
                passwordField.setText("");
                determinePasswordField.setText("");
            }
        });

        logon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String accountNo = accountLabel.getText();
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                String determinePassword = new String(determinePasswordField.getPassword()).trim();
                register(accountNo,username,password,determinePassword);
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                PageSwitch.loginFrame.setVisible(true);
            }
        });
    }

    public void updateData() {
        usernameField.setText("");
        passwordField.setText("");
        determinePasswordField.setText("");
    }

    private void register(String accountNo,String userName, String password1, String password2) {
        if (userName.length()>20) {
            JOptionPane.showMessageDialog(new JFrame(),"注册失败！\n用户名过长！请不要超过20个英文字符（或10个中文字符）");
            return;
        }
        if (password1.length() > 20) {
            JOptionPane.showMessageDialog(new JFrame(),"注册失败\n密码过长！请不要超过20个英文字符");
            return;
        }
        if (password1.contains(" ") || password2.contains(" ")) {
            JOptionPane.showMessageDialog(new JFrame(), "注册失败\n密码中不能有空格");
        } else if (password1.length()>=6 && !"".equals(password2)) {
            if (password1.equals(password2)) {
                boolean flag = userInfo.logon(accountNo,userName,password1);
                if (flag) {
                    updateData();
                    dispose();
                    PageSwitch.loginFrame.setVisible(true);
                    JOptionPane.showMessageDialog(new JFrame(),"注册成功");
                } else {
                    JOptionPane.showMessageDialog(new JFrame(),"此用户已存在");
                }
            } else {
                JOptionPane.showMessageDialog(new JFrame(),"注册失败\n密码不一致");
            }
        } else if ("".equals(password1)) {
            JOptionPane.showMessageDialog(new JFrame(),"注册失败\n密码不能为空");
        } else if("".equals(password2)) {
            JOptionPane.showMessageDialog(new JFrame(),"注册失败\n确认密码不能为空");
        } else {
            JOptionPane.showMessageDialog(new JFrame(),"注册失败\n密码过短");
        }
    }
}
