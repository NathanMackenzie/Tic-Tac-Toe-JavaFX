package window;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import sqlite.SQLiteConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Loads the tabs and add their content for each category.
 */
public class LoadTabView {
    private static TabPane tabView;

    /**
     * @param tabView Tab view to load tabs to
     */
    LoadTabView(TabPane tabView){
        this.tabView = tabView;
        loadTabs();
    }

    /**
     * Loads tabs to tabView depending upon which tabs exist in SQLite database.
     */
    public static void loadTabs(){
        tabView.getTabs().clear();
        Connection connection = new SQLiteConnection().dbConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE user_id = "+ String.valueOf(Window.getID()));

            if(rs.getBoolean("web")){
                tabView.getTabs().add(new LoadTab("Web"));
            }
            if(rs.getBoolean("email")){
                tabView.getTabs().add(new LoadTab("Email"));
            }
            if(rs.getBoolean("apps")){
                tabView.getTabs().add(new LoadTab("Apps"));
            }
            if(rs.getBoolean("bank")){
                tabView.getTabs().add(new LoadTab("Bank"));
            }

            stmt.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
    This private static class extends javafx.scene.control.Tab and creates and handles all design and functionality of
    of the tab and its respective list/items.
     */
    private static class LoadTab extends Tab{

        private ListView<String> list = new ListView<String>();
        private ObservableList data = FXCollections.observableArrayList();

        LoadTab(String table){
            cellFactory();
            setText(table);
            setStyle("-fx-background-color: #FF8A00;");
            setContent(list);
            list.setItems(data);
            list.setStyle("-fx-padding: -2px;");
            list.setId(table);

            loadList(table);
            addListeners();

        }

        // Decorates each cell within the list depending upon if it's empty, full, or selected.
        private void cellFactory(){
            list.setCellFactory(new javafx.util.Callback<ListView<String>, ListCell<String>>() {
                @Override
                public ListCell<String> call(ListView<String> p) {

                    ListCell<String> cell = new ListCell<String>() {

                        @Override
                        protected void updateItem(String item, boolean bln) {
                            super.updateItem(item, bln);

                            if(item != null && WindowController.getItemSelected() != null
                                    && WindowController.getSelectedList() != null &&
                                    item.equals(WindowController.getItemSelected())){
                                setStyle("-fx-background-color: #181818;");
                                Pane pane = new Pane();
                                pane.setStyle("-fx-background-color: transparent;");
                                Label label = new Label(item);
                                label.setStyle("-fx-text-fill: #FF8A00; -fx-font-size: 16px;");
                                pane.getChildren().add(label);
                                setGraphic(pane);
                            }else if(item != null){
                                setStyle("-fx-background-color: #333333;");
                                Pane pane = new Pane();
                                pane.setStyle("-fx-background-color: transparent;");
                                Label label = new Label(item);
                                label.setStyle("-fx-text-fill: #bfbfbf; -fx-font-size: 16px;");
                                pane.getChildren().add(label);
                                setGraphic(pane);
                            }else if(item == null){
                                setStyle("-fx-background-color: #333333;");
                            }
                        }

                    };

                    return cell;
                }
            });
        }

        // Add listeners for the tab and its list
        private void addListeners() {
            // Listener for whenever a item within the list is selected
            list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (newValue != null){

                        // A bit of a "hack" but necessary to prevent a deadlock between changing a selected item within
                        // the list and getting the list's selection model in the listener down below.
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                WindowController.setItemSelected(newValue);
                            }
                        });

                        WindowController.setListSelected(list.getId());
                    }
                }
            });

            // Listener for when the selected list is changed, to change the cell factory to unselect a selected item
            // in the list
            WindowController.getItemSelectedProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if(WindowController.getSelectedList() != null
                            && !WindowController.getSelectedList().equals(list.getId())){
                        list.getSelectionModel().clearSelection();
                    }

                    cellFactory();
                }
            });
        }

        // Add items onto the list
        private void loadList(String table) {
            Connection connection = SQLiteConnection.dbConnection();

            try {
                String sql = "SELECT * FROM " + table + " WHERE user_id = ?";
                PreparedStatement pst = connection.prepareStatement(sql);
                pst.setString(1, String.valueOf(Window.getID()));

                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    data.add(rs.getString("Name"));
                }

                rs.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
