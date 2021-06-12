import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import org.apache.log4j.Logger;


public class loginMenu extends JFrame {
    static JFrame loginFrame;
    private JTextField loginField;
    private JPanel loginPanel;
    private JPasswordField passwordField1;
    private JButton login;
    private JButton exit;
    private JLabel errormsg;
    public static mainMenu mainFrame;
    public static String currentUser;
    public static SQLHandler dbhandler;
    public static Browse browseFrame;
    public static AddMenu addFrame;
    public static editBugsMenu editBugsFrame;
    public static browseUsers browseUsersFrame;
    public static addUsersMenu addUsersFrame;
    public static editUserMenu editUserFrame;
    public final Logger logger = Logger.getLogger("loginMenu");

    static {
        try {
            dbhandler = new SQLHandler();
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
    public loginMenu(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(loginPanel);
        logger.info("Opened login panel");

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String passwd_ = String.valueOf(passwordField1.getPassword());
                String login_ = loginField.getText();
                try {
                    if(dbhandler.CheckLogin(login_, passwd_)){
                        currentUser = login_;
                        mainFrame = new mainMenu("Main Menu");
                        mainFrame.setSize(500, 500);
                        mainFrame.setVisible(true);
                        errormsg.setText("");
                        loginField.setText("");
                        passwordField1.setText("");
                        loginFrame.setVisible(false);
                        logger.info("Logged in");
                    } else {
                        errormsg.setText("Incorrect credentials");
                        logger.info("Incorrect credentials");
                    }

                } catch (SQLException throwables){
                    throwables.printStackTrace();
                }

            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("Exited app");
                loginFrame.dispatchEvent(new WindowEvent(loginFrame, WindowEvent.WINDOW_CLOSING));
            }
        });
    }

    public static void main(String[] args) {
        loginFrame = new loginMenu("Login Menu");
        loginFrame.setSize(500, 500);
        loginFrame.setVisible(true);
    }
}
