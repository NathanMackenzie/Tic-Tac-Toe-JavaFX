package login.add;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This class creates a stage for adding a new user.
 */
public class AddUser {
    private Stage stage;

    private double sceneX;
    private double sceneY;

    /**
     * Default constructor...just so it'll compile.
     */
    public AddUser(){}

    /**
     * Initializes the given stage with a new stage for creating a new user.
     *
     * @param primaryStage The stage of the previous window
     */
    public AddUser(Stage primaryStage){
        stage = primaryStage;
        Parent root;

        try{
            root = FXMLLoader.load(getClass().getResource("add_user.fxml"));
        }catch (Exception e){
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(root, 250, 350, Color.TRANSPARENT);
        stage.setScene(scene);

        // Add listeners
        scene.setOnMouseDragged(e -> mouseDragged(e));
        scene.setOnMousePressed(e -> mousePressed(e));

    }

    private void mouseDragged(MouseEvent e){
        stage.setX(e.getScreenX()-sceneX);
        stage.setY(e.getScreenY()-sceneY);
    }

    private void mousePressed(MouseEvent e){
        sceneX = e.getSceneX();
        sceneY = e.getSceneY();
    }
}
