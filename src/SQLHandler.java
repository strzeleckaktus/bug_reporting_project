import org.apache.log4j.Logger;

import java.sql.*;
import static java.sql.DriverManager.*;


public class SQLHandler {
    static Connection connection = null;
    private static PreparedStatement stmt = null;
    private static ResultSet result = null;
    private static final String URL = "jdbc:sqlite:/C:\\Users\\strze\\Dev\\mgmt_system\\DB\\database.db";
    private static final Logger loggersql = Logger.getLogger("SQLHandler");
    public static void main(String[] args) {
    }

    public SQLHandler() throws SQLException {
        connection = getConnection(URL);
        loggersql.info("Connected to database");
    }

    public static boolean CheckLogin(String login_, String passwd_) throws SQLException {
        String sql = "SELECT CASE WHEN COUNT(PASSWD) > 0 THEN 1 ELSE 0 END AS CORRECT FROM USERS WHERE LOGIN = \"" + login_ + "\" AND PASSWD = \"" + passwd_ + "\";";
        boolean output = false;
        try {
            stmt = connection.prepareStatement(sql);
            result = stmt.executeQuery();
            output = Integer.parseInt(result.getString("CORRECT")) == 1;

        } catch (SQLException throwables){
            throwables.printStackTrace();
        } finally {
            stmt.close();
            result.close();
        }
        if(output){
            loggersql.info("Correct credentials, logged in");
        } else {
            loggersql.info("Incorrect credentials");
        }
        return output;
    }

    public static String showSelectedBug(String id_) throws SQLException{
        String sql = "SELECT * FROM BUGS WHERE \"ID\" = \""+id_+"\";";
        String output = "";

        try{
            stmt = connection.prepareStatement(sql);
            result = stmt.executeQuery();
            String personReporting = result.getString("USER");
            String typeOfBug = result.getString("TYPE");
            String desc = result.getString("DESC");
            String solved = result.getString("SOLVED");

            output += ("Person reporting: " + personReporting + "\nType of Bug: "+ typeOfBug + "\nDescription: " + desc + "\nIs it solved? " + solved + "\n\n");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            stmt.close();
            result.close();
        }
        loggersql.info("showed selected bug");
        return "<html>" + output.replaceAll("\n", "<br>");

    }

    public static String showSelectedUser(String id_) throws SQLException{
        String sql = "SELECT * FROM USERS WHERE \"ID\" = \""+id_+"\";";
        String output = "";
        try {
            stmt = connection.prepareStatement(sql);
            result = stmt.executeQuery();

            while (result.next()){
                String login = result.getString("LOGIN");
                String passwd = result.getString("PASSWD");

                output += ("Login: " + login + "\nPassword: "+ passwd + "\n\n");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            stmt.close();
            result.close();
        }
        loggersql.info("showed selected user");
        return "<html>" + output.replaceAll("\n", "<br>");
    }


    public static void addRecordBug(String type, String desc) throws SQLException{
        String sql = "INSERT INTO BUGS (USER, TYPE, DESC) VALUES ('"+loginMenu.currentUser+"', '" + type + "', '"+ desc +"');";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.execute();
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        loggersql.info("Added a bug");
    }

    public static void addRecordUser(String login_, String password_) throws SQLException{
        String sql = "INSERT INTO USERS (LOGIN, PASSWD) VALUES ('"+login_+"', '"+password_+"');";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.execute();
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        loggersql.info("Added a user");
    }
    public static void setAsSolved(String id_) throws SQLException{
        String sql = "UPDATE BUGS SET SOLVED = \"yes\" where ID = " + id_ + ";";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.execute();
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        loggersql.info("Bug set as solved");
    }

    public static void changeType(String id_, String type_) throws SQLException{
        String sql = "UPDATE BUGS SET TYPE = \"" + type_ + "\" where ID = " + id_ +";";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.execute();
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        loggersql.info("Changed type of bug");
    }

    public static void changeLogin(String id_, String login_) throws SQLException{
        String sql = "UPDATE USERS SET LOGIN = \"" + login_ + "\" where ID = " + id_ +";";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.execute();
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        loggersql.info("Changed login of user");
    }

    public static void changePassword(String id_, String password_) throws SQLException{
        String sql = "UPDATE USERS SET PASSWD = \"" + password_ + "\" where ID = " + id_ +";";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.execute();
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        loggersql.info("Changed password");
    }

    public static String curUser(String id_) throws SQLException{
        String sql = "SELECT * FROM USERS WHERE ID = "+id_+";";
        String output = "";
        try {
            stmt = connection.prepareStatement(sql);
            result = stmt.executeQuery();
            output = result.getString("LOGIN");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            stmt.close();
            result.close();
        }
        loggersql.info("Current user is " + output);
        return output;
    }

    public static void changeDesc(String id_, String desc_) throws SQLException{
        String sql = "UPDATE BUGS SET DESC = \"" + desc_ + "\" where ID = " + id_ +";";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.execute();
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        loggersql.info("Description of bug changed");
    }

    public static void deleteEntry(String id_, String table_) throws SQLException{
        String sql = "DELETE FROM "+table_+" WHERE ID =\""+id_+"\";";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.execute();
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        loggersql.info("Deleted an entry from " + table_);
    }

    public static String getIDs(String table_) throws SQLException{
        String sql = "SELECT ID FROM " + table_ +";";
        String output = "";

        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            result = stmt.executeQuery();
            while (result.next()){
                output += (result.getString("ID") + "|");
            }
            output = output.substring(0, output.length()-1);
        } catch (SQLException throwables){
            throwables.printStackTrace();
        } finally {
            result.close();
        }
        loggersql.info("Got ID's");
        return output;

    }
}
