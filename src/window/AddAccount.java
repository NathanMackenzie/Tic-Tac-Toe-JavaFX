package window;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This class creates a window modal for adding a new account.
 */
public class AddAccount {
    private static Stage stage;

    /**
     * Creates a modal window and initializes its scene.
     */
    public AddAccount(){
        stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("add.fxml"));
        }catch (Exception e){
            e.printStackTrace();
        }

        // TODO: fix render lag when displaying the scene after the stage has been made visible
        Scene scene = new Scene(root, 550, 450, new Color(0, 0, 0, .85));
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Window.stage);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    /**
     * Closes window
     */
    public static void close(){
        stage.close();
    }
}
