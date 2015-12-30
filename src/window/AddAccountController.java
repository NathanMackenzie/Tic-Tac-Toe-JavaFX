package window;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;

/**
 * This class handles the control functionality of add.fxml
 */
public class AddAccountController {

    @FXML
    ChoiceBox choiceBox;

    @FXML
    Pane displayLayout;

    /**
     * Initializes the necessary listeners for the layout.
     */
    @FXML
    public void initialize(){
        // Add choice box listener to call the proper layout depending upon selected
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                switch (newValue.intValue()){
                    case 0:
                        loadWebsite();
                        break;
                    case 1:
                        loadEmail();
                        break;
                    case 2:
                        loadApps();
                        break;
                    case 3:
                        loadBank();
                        break;
                }
            }
        });
    }

    /*
    All remaining private methods load their respective
    layout files and set them as displayLayout
     */

    private void loadWebsite(){
        Pane root;
        try {
            root = FXMLLoader.load(getClass().getResource("resource/website/add/websites.fxml"));
        }catch (Exception e){
            root = null;
            e.printStackTrace();
        }
        displayLayout.getChildren().clear();
        displayLayout.getChildren().add(root);
    }

    private void loadEmail(){
        Pane root;
        try {
            root = FXMLLoader.load(getClass().getResource("resource/email/add/email.fxml"));
        }catch (Exception e){
            root = null;
            e.printStackTrace();
        }
        displayLayout.getChildren().clear();
        displayLayout.getChildren().add(root);
    }

    private void loadApps(){
        Pane root;
        try {
            root = FXMLLoader.load(getClass().getResource("resource/apps/add/apps.fxml"));
        }catch (Exception e){
            root = null;
            e.printStackTrace();
        }
        displayLayout.getChildren().clear();
        displayLayout.getChildren().add(root);
    }

    private void loadBank(){
        Pane root;
        try {
            root = FXMLLoader.load(getClass().getResource("resource/bank/add/bank.fxml"));
        }catch (Exception e){
            root = null;
            e.printStackTrace();
        }
        displayLayout.getChildren().clear();
        displayLayout.getChildren().add(root);
    }
}
