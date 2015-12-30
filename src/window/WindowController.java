package window;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import sqlite.SQLiteConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Handles control functionality of window.fxml
 */
public class WindowController {
    private static SimpleStringProperty listSelected = new SimpleStringProperty(null);
    private static SimpleStringProperty itemSelected = new SimpleStringProperty(null);

    @FXML
    TabPane tabView;

    @FXML
    Pane viewPane;

    @FXML
    Button editButton;

    /**
     * Loads tabView and adds the viewPane layout.
     */
    @FXML
    public void initialize(){
        new LoadTabView(tabView);
        //new GeneralList(generalList);
        viewPane.getChildren().add(new LoadViewPane());
    }

    /**
     * Change if the content within viewPane is editable or not
     */
    @FXML
    public void changeEditable(){
        if(LoadViewPane.editable.getValue()){
            editButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black;");
        }else{
            editButton.setStyle("-fx-background-color: #bfbfbf; -fx-text-fill: black;");

        }
        LoadViewPane.editable.setValue(!LoadViewPane.editable.getValue());
    }

    /**
     * Creates object of AddAccount to open a modal utility window to add new user
     */
    @FXML
    public void add(){
        new AddAccount();
    }

    /**
     * Deletes currently selected item from the database.
     */
    @FXML
    public void delete(){
        String sql = "DELETE FROM "+ getSelectedList()+" WHERE name = '"+itemSelected.getValue()+"';";
        SQLiteConnection.editDatabase(sql);

        boolean empty = true;

        Connection connection = SQLiteConnection.dbConnection();
        String query = "SELECT user_id FROM "+ getSelectedList();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                if(rs.getInt("user_id") == Window.getID()){
                    empty = false;
                    break;
                }
            }
            rs.close();
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        if(empty){
            SQLiteConnection.editDatabase("UPDATE users SET "+ getSelectedList()+" = 0 WHERE user_id = "+Window.getID());
        }

        LoadTabView.loadTabs();
    }

    /**
     * Get name of the currently selected list.
     *
     * @return String name of selected list
     */
    public static String getSelectedList(){
        return listSelected.getValue();
    }

    /**
     * Change the selected list.
     *
     * @param list String of list name to select
     */
    public static void setListSelected(String list){
        listSelected.setValue(list);
    }

    /**
     * Get the simple string property of listSelected property.
     *
     * @return SimpleStringProperty of list selected property
     */
    public static SimpleStringProperty getListSelectedProperty(){
        return listSelected;
    }

    /**
     * Get the name of the item selected.
     *
     * @return String of name of item selected
     */
    public static String getItemSelected(){
        return itemSelected.getValue();
    }

    /**
     * Set the selected item.
     *
     * @param item String value of item to select
     */
    public static void setItemSelected(String item){
        itemSelected.setValue(item);
    }

    /**
     * Get simple string property of itemSelected.
     *
     * @return SimpleStringProperty of itemSelected
     */
    public static SimpleStringProperty getItemSelectedProperty(){
        return itemSelected;
    }
}
