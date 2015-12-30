package window;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

/**
 * Adds the proper layout to the viewPane from window.fxml
 */
public class LoadViewPane extends Pane{
    private String currentTab;
    //
    public static SimpleBooleanProperty editable = new SimpleBooleanProperty(false);

    private Label text = new Label("Cryptic");

    public LoadViewPane(){
        addListeners();

        Reflection reflection = new Reflection();

        text.setStyle("-fx-text-fill: FF8A00;");
        text.setLayoutY(40);
        text.setFont(Font.font("Iowan Old Style", 70));
        text.setEffect(reflection);

        getChildren().add(text);
    }

    // Adds listeners for whenever a new item is selected or the width of the window changes
    private void addListeners(){
        WindowController.getItemSelectedProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!WindowController.getSelectedList().equals(currentTab)) {
                    loadNode(WindowController.getSelectedList());
                }
                editable.setValue(false);
            }
        });

        Window.widthProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                double layoutWidth = Double.parseDouble(newValue.toString())-200;
                text.setLayoutX((layoutWidth/2)-(text.getWidth()/2));
            }
        });

    }

    // Loads the appropriate layout for the pane
    private void loadNode(String tab){
        Node node;
        getChildren().clear();
        try{
            if(tab.equals("Web")){
                node = (Node) FXMLLoader.load(getClass().getResource("resource/website/view/website_viewpane.fxml"));
                getChildren().add(node);
            }else if(tab.equals("Email")){
                node = (Node) FXMLLoader.load(getClass().getResource("resource/email/view/email_viewpane.fxml"));
                getChildren().add(node);
            }else if(tab.equals("Apps")){
                node = (Node) FXMLLoader.load(getClass().getResource("resource/apps/view/apps_viewpane.fxml"));
                getChildren().add(node);
            }else if(tab.equals("Bank")){
                node = (Node) FXMLLoader.load(getClass().getResource("resource/bank/view/banks_viewpane.fxml"));
                getChildren().add(node);
            }else if(tab.equals("General")){
                node = (Node) FXMLLoader.load(getClass().getResource("resource/general/contacts.fxml"));
                getChildren().add(node);
            }
        }catch (Exception e){
           e.printStackTrace();
            node = null;
        }



    }
}
