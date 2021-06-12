import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class editUserMenu extends JFrame {
    private JTextField loginField;
    private JPanel editUserPane;
    private JTextField passwordField;
    private JButton submitButtonPassword;
    private JButton returnButton;
    private JButton submitButtonLogin;
    private JLabel msgToUser;
    public final Logger logger = Logger.getLogger("EditUserMenu");

    public editUserMenu(String id_){
        super("Edit users menu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(editUserPane);
        logger.info("Opened edit user panel");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("returned to browse users panel");
                loginMenu.browseUsersFrame = new browseUsers("Browse bugs");
                loginMenu.browseUsersFrame.setSize(500, 500);
                loginMenu.browseUsersFrame.setVisible(true);
                loginMenu.editUserFrame.setVisible(false);

            }
        });

        submitButtonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SQLHandler.changeLogin(id_, loginField.getText());
                    msgToUser.setText("Login changed!");
                } catch (SQLException throwables){
                    throwables.printStackTrace();
                }
            }
        });

        submitButtonPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SQLHandler.changePassword(id_, passwordField.getText());
                    msgToUser.setText("Password changed!");
                } catch (SQLException throwables){
                    throwables.printStackTrace();
                }
            }
        });
    }
}
