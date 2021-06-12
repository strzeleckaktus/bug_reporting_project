import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class addUsersMenu extends JFrame {
    private JButton submitButton;
    private JPanel addUsersPane;
    private JTextField passwordField;
    private JTextField loginField;
    private JButton returnButton;
    public final Logger logger = Logger.getLogger("AddUsersMenu");

    public addUsersMenu(){
        super("Add users menu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(addUsersPane);
        logger.info("Opened Add users panel");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    SQLHandler.addRecordUser(loginField.getText(), passwordField.getText());
                } catch(SQLException throwables) {
                    throwables.printStackTrace();
                }
                loginMenu.browseUsersFrame = new browseUsers("Browse bugs");
                loginMenu.browseUsersFrame.setSize(500, 500);
                loginMenu.browseUsersFrame.setVisible(true);
                loginMenu.addUsersFrame.setVisible(false);
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginMenu.browseUsersFrame = new browseUsers("Browse bugs");
                loginMenu.browseUsersFrame.setSize(500, 500);
                loginMenu.browseUsersFrame.setVisible(true);
                loginMenu.addUsersFrame.setVisible(false);
                logger.info("Returned to browse Users menu");
            }
        });
    }
}
