package window.resource.email.add;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sqlite.SQLiteConnection;
import window.LoadTabView;
import window.Window;
import window.AddAccount;

import java.sql.PreparedStatement;

public class EmailController {
    @FXML
    TextField name;

    @FXML
    TextField address;

    @FXML
    TextField username;

    @FXML
    TextField password;

    @FXML
    Label errorMessage;

    @FXML
    public void add(){
        String sql = "CREATE TABLE IF NOT EXISTS emails(" +
                "user_id INT NOT NULL," +
                "name TEXT NOT NULL," +
                "address TEXT NOT NULL," +
                "username TEXT NOT NULL," +
                "password TEXT NOT NULL);";

        SQLiteConnection.editDatabase(sql);

        if(name.getText().equals("") || address.getText().equals("") || username.getText().equals("")
                || password.getText().equals("")){
            errorMessage.setText("Incomplete Field");
            return;
        }

        if(SQLiteConnection.exist("email", "name", name.getText())){
            errorMessage.setText("Account Already Exists");
            return;
        }

        try {
            String insertTableSQL = "INSERT INTO email"
                    + "(user_id, name, address, username, password) VALUES"
                    + "(?,?,?,?,?)";
            PreparedStatement pst = SQLiteConnection.dbConnection().prepareStatement(insertTableSQL);
            pst.setInt(1, Window.getID());
            pst.setString(2, name.getText());
            pst.setString(3, address.getText());
            pst.setString(4, username.getText());
            pst.setString(5, password.getText());

            pst.executeUpdate();
            pst.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        sql = "UPDATE users " +
                "SET email = 1 "+
                "WHERE user_id = " + String.valueOf(Window.getID());

        SQLiteConnection.editDatabase(sql);

        AddAccount.close();
        LoadTabView.loadTabs();
    }

    @FXML
    public void cancel(){
        AddAccount.close();
    }
}
