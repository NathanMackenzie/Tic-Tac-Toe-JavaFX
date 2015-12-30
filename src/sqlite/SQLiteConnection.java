package sqlite;

import login.Login;

import java.sql.*;

public class SQLiteConnection {

    public static Connection dbConnection(){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:/Users/natemackenzie/IdeaProjects/Cryptic/src/sqlite/Cryptic.sqlite");
            return connection;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns true and logs into the users account if the account exist, otherwise returns false.
     *
     * @param userName The username
     * @param password The password
     * @return true if item was found
     */
    public static boolean login(String userName, String password){
        boolean found = false;
        Connection connection = SQLiteConnection.dbConnection();
        ResultSet rs = null;
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Users");
            while (rs.next()) {
                if (rs.getString("UserName").equals(userName) && rs.getString("Password").equals(password)){
                    Login.signIn(rs.getRow());
                    found = true;
                    break;
                }
            }
            stmt.close();
            rs.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return found;

    }

    /**
     * This edits the database.
     *
     * @param sql The SQL to execute
     */
    public static void editDatabase(String sql){
        Connection connection = dbConnection();

        try{
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.executeUpdate();
            pst.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This checks to see if an item already exist within a column of a sql table, returning false if it isn't.
     *
     * @param table The table name
     * @param column The column name
     * @param item The text string of item being tested
     * @return True if item already exist within said column
     */
    public static boolean exist(String table, String column, String item){
        Connection connection = dbConnection();

        try{
            String sql = "SELECT * FROM "+table;
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                if(rs.getString(column).equals(item))
                    return true;
            }

            stmt.close();
            rs.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
