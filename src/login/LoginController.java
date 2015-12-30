package login;

import sqlite.SQLiteConnection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * This class handles the control functionality of login.fxml
 */
public class LoginController {

    @FXML
    TextField userNameField;

    @FXML
    TextField passwordField;

    @FXML
    Label errorMessage;

    /**
     * Checks that password and username fields aren't empty then attempts to login, displaying error message
     * if login's unsuccessful.
     */
    @FXML
    public void login(){

        if(userNameField.getText().equals("") || passwordField.getText().equals("")){
            errorMessage.setText("Empty Field");
            return;
        }

        //TODO: Protect against SQL injections

        // Attempt to login
        if(!SQLiteConnection.login(userNameField.getText(), passwordField.getText()))
            errorMessage.setText("Incorrect Username or Password");

    }

    /**
     * Calls Login.newUser() to switch scene to create a new user.
     */
    @FXML
    public void newUser(){
        Login.newUser();
    }

    /**
     * Exits the application.
     */
    @FXML
    public void close(){
        Platform.exit();
    }

}
