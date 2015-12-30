package login.add;

import sqlite.SQLiteConnection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * This class handles the control functionality of add_user.fxml
 */
public class AddUserController {

    @FXML
    TextField userName;

    @FXML
    PasswordField password1;

    @FXML
    PasswordField password2;

    @FXML
    Label errorMessage;

    /**
     * This method creates a new user, displaying an error message if fields are not properly entered or a username or password
     * already exist.
     */
    @FXML
    public void createUser(){
        SQLiteConnection.editDatabase("CREATE TABLE IF NOT EXISTS users(" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "web INT DEFAULT 0," +
                "email INT DEFAULT 0," +
                "apps INT DEFAULT 0," +
                "bank INT DEFAULT 0);");

        // Check both fields have text entered
        if(userName.getText().equals("") || password1.getText().equals("") || password2.getText().equals("")){
            errorMessage.setText("Incomplete Field");
            return;
        }
        // Verify passwords match each other
        if(!password1.getText().equals(password2.getText())){
            errorMessage.setText("Passwords Do Not Match");
            return;
        }
        // Check that the user name isn't already in use
        if(SQLiteConnection.exist("Users", "UserName", userName.getText())){
            errorMessage.setText("User Name Already Exist");
            return;
        }
        // Check that the password isn't already in user
        if(SQLiteConnection.exist("Users", "Password", password1.getText())){
            errorMessage.setText("Password Already Exist");
            return;
        }
        SQLiteConnection.editDatabase("INSERT INTO Users (UserName, Password) " +
                "VALUES('"+userName.getText()+"', '"+ password1.getText()+"')");
        SQLiteConnection.login(userName.getText(), password1.getText());

    }

    /**
     * Exits application.
     */
    @FXML
    public void close(){
        Platform.exit();
    }
}
