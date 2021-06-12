import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddMenu extends JFrame {
    private JPanel addBugPanel;
    private JTextField descriptionField;
    private JButton submitButton;
    private JComboBox bugTypeCombo;
    public final Logger logger = Logger.getLogger("AddMenu");
    public AddMenu(String title, String table){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(addBugPanel);
        logger.info("Opened addBug panel");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    SQLHandler.addRecordBug(String.valueOf(bugTypeCombo.getSelectedItem()), descriptionField.getText());
                } catch(SQLException throwables) {
                    throwables.printStackTrace();
                }
                loginMenu.browseFrame = new Browse("Browse bugs", "BUGS");
                loginMenu.browseFrame.setSize(500, 500);
                loginMenu.browseFrame.setVisible(true);
                loginMenu.addFrame.setVisible(false);

            }
        });
    }
    }
