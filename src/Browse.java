import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Browse extends JFrame{
    private JPanel browsePanel;
    private JButton returnButton;
    private JButton addButton;
    private JButton editButton;
    private JLabel records;
    private JComboBox comboBox;
    private JButton deleteEntryButton;
    private JLabel errormsg;
    private String bugList;
    private String idNumber;
    public final Logger logger = Logger.getLogger("BrowseBugsMenu");

    public Browse(String title, String table){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(browsePanel);
        logger.info("Opened browseBugs panel");
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!comboBox.getSelectedItem().toString().equals("Select a bug to display")){
                    try{
                        records.setText(SQLHandler.showSelectedBug(comboBox.getSelectedItem().toString()));
                        idNumber = comboBox.getSelectedItem().toString();
                    } catch (SQLException throwables){
                        throwables.printStackTrace();
                    }
                } else {
                    records.setText("");
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!comboBox.getSelectedItem().toString().equals("Select a bug to display")) {
                    loginMenu.editBugsFrame = new editBugsMenu(idNumber, "Edit Bugs Menu");
                    loginMenu.editBugsFrame.setSize(500, 500);
                    loginMenu.editBugsFrame.setVisible(true);
                    loginMenu.browseFrame.setVisible(false);
                    logger.info("Pressed edit bug");
                } else {
                    errormsg.setText("Please select a correct entry");
                }
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginMenu.mainFrame.setVisible(true);
                loginMenu.browseFrame.setVisible(false);
                logger.info("Returned to main menu");
            }
        });

        deleteEntryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!comboBox.getSelectedItem().toString().equals("Select a bug to display")) {
                    try {
                        SQLHandler.deleteEntry(idNumber, "BUGS");
                        loginMenu.browseFrame.setVisible(false);
                        loginMenu.browseFrame = new Browse("Browse Bugs", "BUGS");
                        loginMenu.browseFrame.setSize(500, 500);
                        loginMenu.browseFrame.setVisible(true);
                        logger.info("Deleted a bug");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else {
                    errormsg.setText("Please select a correct entry");
                    logger.info("Incorrect choice in delete bug");
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginMenu.addFrame = new AddMenu("Add Menu", table);
                loginMenu.addFrame.setSize(500, 500);
                loginMenu.addFrame.setVisible(true);
                loginMenu.browseFrame.setVisible(false);
                logger.info("Opened add bug menu");
            }
        });

        comboBox.addItem("Select a bug to display");
        try{
            bugList = SQLHandler.getIDs("BUGS");
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        String[] bugs = bugList.split("\\|");
        for (String s : bugs) {
            comboBox.addItem(s);
        }
    }
}
