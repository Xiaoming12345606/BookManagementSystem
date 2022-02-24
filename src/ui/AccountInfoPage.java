package ui;

import tools.ImageInput;
import tools.PageSwitch;
import tools.UserInfo;
import user.Administrator;
import user.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class AccountInfoPage extends JPanel {
    private final UserInfo userInfo = new UserInfo();
    User user;
    private String oldPassword;
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JLabel label1 = new JLabel("账   号：");
    JLabel label2 = new JLabel("用户名：");
    JLabel label3 = new JLabel("密   码：");
    protected JLabel account = new JLabel();
    protected JLabel username = new JLabel();
    JLabel title = new JLabel("账户信息");
    JLabel bottom = new JLabel("注销账户？");
    JPanel bottomPanel = new JPanel();
    protected JPasswordField password = new JPasswordField();



    JButton modify = new JButton("修改");
    JButton cancel = new JButton("取消");
    public AccountInfoPage() {
        this.setPreferredSize(new Dimension(1000,560));
        controlInit();
        listeners();
        this.add(title);
        this.add(panel1);
        this.add(panel2);
        this.add(bottomPanel);
    }

    public void controlInit() {
        title.setPreferredSize(new Dimension(1000,60));
        title.setFont(new Font("microsoft YaHei",Font.BOLD,28));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setIcon(new ImageIcon(new ImageInput(new File("src/images/myAccount.png")).getImage()));

        bottomPanel.setPreferredSize(new Dimension(1000,40));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,18));
        bottom.setFont(new Font("microsoft YaHei",Font.PLAIN,14));
        bottom.setForeground(new Color(0x316FF3));
        bottom.setHorizontalAlignment(SwingConstants.CENTER);
        bottomPanel.add(bottom);

        panel1.setLayout(new FlowLayout(FlowLayout.CENTER,20,50));
        panel1.setPreferredSize(new Dimension(400,300));
        password.setPreferredSize(new Dimension(150,30));
        password.setBorder(new LineBorder(new Color(0x7DC3F1), 1, true));
        account.setPreferredSize(new Dimension(150,30));
        username.setPreferredSize(new Dimension(150,30));
        password.setPreferredSize(new Dimension(150,30));

        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label3.setHorizontalAlignment(SwingConstants.CENTER);
        username.setHorizontalAlignment(SwingConstants.CENTER);
        account.setHorizontalAlignment(SwingConstants.CENTER);

        label1.setFont(new Font("microsoft YaHei",Font.BOLD,20));
        label2.setFont(new Font("microsoft YaHei",Font.BOLD,20));
        label3.setFont(new Font("microsoft YaHei",Font.BOLD,20));
        username.setFont(new Font("microsoft YaHei",Font.BOLD,20));
        password.setFont(new Font("microsoft YaHei",Font.BOLD,20));
        account.setFont(new Font("microsoft YaHei",Font.BOLD,20));

        panel1.add(label1);
        panel1.add(account);
        panel1.add(label2);
        panel1.add(username);
        panel1.add(label3);
        panel1.add(password);

        label1.setIcon(new ImageIcon(new ImageInput(new File("src/images/account.png")).getImage()));
        label2.setIcon(new ImageIcon(new ImageInput(new File("src/images/user.png")).getImage()));
        label3.setIcon(new ImageIcon(new ImageInput(new File("src/images/password.png")).getImage()));

        panel2.setLayout(new FlowLayout(FlowLayout.CENTER,60,60));
        panel2.setPreferredSize(new Dimension(1000,100));
        modify.setPreferredSize(new Dimension(60,30));
        cancel.setPreferredSize(new Dimension(60,30));
        modify.setFocusPainted(false);
        cancel.setFocusPainted(false);
        modify.setBorder(new LineBorder(new Color(0x7DC3F1), 1, true));
        cancel.setBorder(new LineBorder(new Color(0x7DC3F1), 1, true));
        panel2.add(modify);
        panel2.add(cancel);

    }

    public void listeners() {
        modify.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                modifyPwd();
            }
        });

        bottom.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (user instanceof Administrator) {
                    JOptionPane.showMessageDialog(new JFrame(),"注销失败！\n管理员不能通过此处注销！");
                    return;
                }
                String accountNo = account.getText();
                int option = JOptionPane.showOptionDialog(new JFrame(),"是否确认注销此账户？","系统提示",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,new String[]{"确定","取消"},null);
                if (option == JOptionPane.OK_OPTION) {
                    logOff(accountNo);
                } else {
                    JOptionPane.showMessageDialog(new JFrame(),"已取消注销该账户！\n你可以继续使用！");
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                bottom.setForeground(new Color(0x626264));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                bottom.setForeground(new Color(0x316FF3));
            }
        });

        cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                password.setText(oldPassword);
            }
        });
    }

    public void modifyPwd() {
        String accountNo = account.getText();
        String newPwd = new String(password.getPassword()).trim();
        int option = JOptionPane.showOptionDialog(new JFrame(),"是否确认修改？","系统提示",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,new String[]{"确定","取消"},null);
        if (newPwd.length() < 6) {
            password.setText(oldPassword);
            JOptionPane.showMessageDialog(new JFrame(),"修改密码失败！\n密码长度过短！");
            return;
        } else if (newPwd.contains(" ")) {
            password.setText(oldPassword);
            JOptionPane.showMessageDialog(new JFrame(),"修改密码失败！\n密码中不能包含空格！");
            return;
        }
        if (option == JOptionPane.OK_OPTION) {
            if (user instanceof Administrator) {
                password.setText(oldPassword);
                JOptionPane.showMessageDialog(new JFrame(),"修改密码失败！\n管理员修改密码不在此处提供！");
            } else {
                if (userInfo.modifyPwd(accountNo,newPwd)) {
                    password.setText(newPwd);
                    JOptionPane.showMessageDialog(new JFrame(),"修改密码成功！");
                } else {
                    JOptionPane.showMessageDialog(new JFrame(),"修改密码失败！");
                }
            }
        } else {
            password.setText(oldPassword);
            JOptionPane.showMessageDialog(new JFrame(),"您已取消修改！");
        }
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void logOff(String accountNO) {
        if (userInfo.logOff(accountNO)) {
            JOptionPane.showMessageDialog(new JFrame(),"注销账户成功！");
            PageSwitch.mainFrame.setVisible(false);
            PageSwitch.loginFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(new JFrame(),"注销账户失败！\n请先将借阅的图书归还后重试！");
        }
    }
}
