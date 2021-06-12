import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainMenu extends JFrame {

    private JButton browseBugsButton;
    private JPanel mainMenuPanel;
    private JButton usersButton;
    private JButton logoutButton;
    public final Logger logger = Logger.getLogger("MainMenu");

    public mainMenu(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainMenuPanel);
        logger.info("Opened Main panel");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginMenu.currentUser = "";
                loginMenu.loginFrame.setVisible(true);
                loginMenu.mainFrame.setVisible(false);
                logger.info("Logged out");
            }
        });

        browseBugsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginMenu.browseFrame = new Browse("Browse bugs", "BUGS");
                loginMenu.browseFrame.setSize(500, 500);
                loginMenu.browseFrame.setVisible(true);
                loginMenu.mainFrame.setVisible(false);
            }
        });

        usersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginMenu.browseUsersFrame = new browseUsers("Browse users");
                loginMenu.browseUsersFrame.setSize(500, 500);
                loginMenu.browseUsersFrame.setVisible(true);
                loginMenu.mainFrame.setVisible(false);
            }
        });
    }
}
