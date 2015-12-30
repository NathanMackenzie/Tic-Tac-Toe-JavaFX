package window.resource.email.view;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sqlite.SQLiteConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import window.LoadViewPane;
import window.Window;
import window.WindowController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmailController {

    @FXML
    AnchorPane layout;

    @FXML
    Label name;

    @FXML
    TextField address;

    @FXML
    TextField username;

    @FXML
    TextField password;

    public void initialize(){
        setValues(WindowController.getItemSelected());
        setEditable(false);
        layout.setPrefWidth(Double.parseDouble(Window.widthProperty().getValue().toString().toString())-200);

        LoadViewPane.editable.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                setEditable(newValue);
                if(!newValue)
                    updateDatabase();
            }
        });

        WindowController.getItemSelectedProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                setValues(newValue);
            }
        });

        Window.widthProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                layout.setPrefWidth(Double.parseDouble(newValue.toString())-200);
            }
        });
    }

    private void setEditable(boolean editable){
        address.setDisable(!editable);
        password.setDisable(!editable);
        username.setDisable(!editable);
    }

    private void updateDatabase(){
        Connection connection = SQLiteConnection.dbConnection();
        //TODO: When updating database because a new item was selected, the new item recieves the values instead of the previous FIXIT
        try {
            String sql = "UPDATE email SET address = ?, username = ?, password = ? WHERE user_id = ? AND name = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, address.getText());
            pst.setString(2, username.getText());
            pst.setString(3, password.getText());
            pst.setString(4, String.valueOf(Window.getID()));
            pst.setString(5, WindowController.getItemSelected());
            pst.executeUpdate();
            pst.close();
            connection.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setValues(String value){

        Connection connection = SQLiteConnection.dbConnection();

        try{
            PreparedStatement pst = connection.prepareStatement("SELECT * FROM email WHERE name = ? AND user_id = ?");
            pst.setString(1, value);
            pst.setInt(2, Window.getID());
            ResultSet rs = pst.executeQuery();

            name.setText(rs.getString("name"));
            address.setText(rs.getString("address"));
            username.setText(rs.getString("username"));
            password.setText(rs.getString("password"));

            rs.close();
            pst.close();
            connection.close();
        }catch (Exception e){
            //e.printStackTrace();
        }
    }
}
