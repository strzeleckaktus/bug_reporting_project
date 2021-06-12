import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class editBugsMenu extends JFrame {

    private JButton returnButton;
    private JPanel editBugsPanel;
    private JButton setAsSolvedButton;
    private JButton submitButtonJText;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JButton submitButtonCombo;
    private JLabel informationLabel;
    public final Logger logger = Logger.getLogger("EditBugsMenu");

    public editBugsMenu(String id_, String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(editBugsPanel);
        logger.info("Opened edit bugs panel");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginMenu.browseFrame = new Browse("Browse bugs", "BUGS");
                loginMenu.browseFrame.setSize(500, 500);
                loginMenu.browseFrame.setVisible(true);
                loginMenu.editBugsFrame.setVisible(false);
                logger.info("Returned to browse bugs panel");
            }
        });

        setAsSolvedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    SQLHandler.setAsSolved(id_);
                    informationLabel.setText("Bug set as solved!");
                } catch (SQLException throwables){
                    throwables.printStackTrace();
                }

            }
        });

        submitButtonCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SQLHandler.changeType(id_, String.valueOf(comboBox1.getSelectedItem()));
                    informationLabel.setText("Bug type changed!");
                } catch (SQLException throwables){
                    throwables.printStackTrace();
                }
            }
        });

        submitButtonJText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SQLHandler.changeDesc(id_, textField1.getText());
                    informationLabel.setText("Bug Description changed!");
                } catch (SQLException throwables){
                    throwables.printStackTrace();
                }
            }
        });
    }
}
