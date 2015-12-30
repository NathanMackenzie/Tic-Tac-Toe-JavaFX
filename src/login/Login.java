package login;

import javafx.scene.paint.Color;
import login.add.AddUser;
import window.Window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Entrance to Cryptic username and password storage application, this class manages
 * setting up the login window.
 */
public class Login extends Application {
    private static Stage stage;

    // Define the coordinate location of a click within the scene
    private double sceneX;
    private double sceneY;

    /**
     * Initializes the stage's settings and loads scene.
     *
     * @param stage The primary stage initially created by the Application
     * @throws Exception This is for exceptions thrown when loading fxml file
     */
    @Override
    public void start(Stage stage) throws Exception{
        this.stage = stage;

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));

        Scene scene = new Scene(root, 250, 350, Color.TRANSPARENT);

        // Add listeners for window dragging
        scene.setOnMouseDragged(e -> mouseDragged(e));
        scene.setOnMousePressed(e -> mousePressed(e));

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Changes scene to add a new user.
     */
    public static void newUser(){
        new AddUser(stage);
    }

    /**
     * Closes this stage and signs into the account of the user.
     *
     * @param ID This is the ID number for the users account.
     */
    public static void signIn(int ID){
        new Window(ID);
        stage.close();
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
