import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class browseUsers extends JFrame {
    private JComboBox comboBoxUsers;
    private JPanel browseUsersPane;
    private JButton editUserButton;
    private JButton deleteUserButton;
    private JButton addUserButton;
    private JButton returnButton;
    private JLabel Content;
    private JLabel errormsg;
    private String userlist;
    private String userid;
    public final Logger logger = Logger.getLogger("BrowseUsersMenu");

    public browseUsers(String Title){
        super(Title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(browseUsersPane);
        logger.info("Opened browse Users panel");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginMenu.mainFrame.setVisible(true);
                loginMenu.browseUsersFrame.setVisible(false);
                logger.info("Returned to main menu");
            }
        });

        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginMenu.browseUsersFrame.setVisible(false);
                loginMenu.addUsersFrame = new addUsersMenu();
                loginMenu.addUsersFrame.setSize(500, 500);
                loginMenu.addUsersFrame.setVisible(true);
                logger.info("Opened Add Users panel");
            }
        });

        editUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!comboBoxUsers.getSelectedItem().toString().equals("Select a user to display")) {
                        loginMenu.editUserFrame = new editUserMenu(userid);
                        loginMenu.editUserFrame.setSize(500, 500);
                        loginMenu.editUserFrame.setVisible(true);
                        loginMenu.browseUsersFrame.setVisible(false);

                } else {
                    errormsg.setText("Please select a correct entry");
                    logger.info("Incorrect entry in edit user");
                }
            }
        });

        comboBoxUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!comboBoxUsers.getSelectedItem().toString().equals("Select a user to display")){
                    try{
                        Content.setText(SQLHandler.showSelectedUser(comboBoxUsers.getSelectedItem().toString()));
                        userid = comboBoxUsers.getSelectedItem().toString();
                    } catch (SQLException throwables){
                        throwables.printStackTrace();
                    }
                } else {
                    Content.setText("");
                }
            }
        });

        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!comboBoxUsers.getSelectedItem().toString().equals("Select a user to display")) {
                    String temp = "";
                    try{
                        temp = SQLHandler.curUser(userid);

                    } catch (SQLException throwables){
                        throwables.printStackTrace();
                    }
                    if(loginMenu.currentUser.equals(temp)) {
                        errormsg.setText("You cannot delete this account, as it is being currently in use");
                        logger.info("Tried deleting current user");
                    } else {
                        try {
                            SQLHandler.deleteEntry(userid, "USERS");
                            loginMenu.browseUsersFrame.setVisible(false);
                            loginMenu.browseUsersFrame = new browseUsers("Browse Bugs");
                            loginMenu.browseUsersFrame.setSize(500, 500);
                            loginMenu.browseUsersFrame.setVisible(true);
                            logger.info("Deleted a user and refreshed window");
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                } else {
                    errormsg.setText("Please select a correct entry");
                    logger.info("Incorrect entry in delete user");
                }
            }
        });

        comboBoxUsers.addItem("Select a user to display");
        try{
            userlist = SQLHandler.getIDs("USERS");
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        String[] bugs = userlist.split("\\|");
        for (String s : bugs) {
            comboBoxUsers.addItem(s);
        }
    }
}
