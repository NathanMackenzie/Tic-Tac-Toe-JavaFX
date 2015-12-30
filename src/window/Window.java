package window;


import javafx.beans.property.ReadOnlyProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Creates the main window for the application.
 */
public class Window {
    public static Stage stage;

    private static int ID;

    public Window(){}

    /**
     * Creates a new window and adds a layout.
     *
     * @param ID The users ID number in the database
     */
    public Window(int ID){
        this.ID = ID;
        stage = new Stage();
        Parent root;
        try{
            root = FXMLLoader.load(getClass().getResource("window.fxml"));
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }

    /**
     * Getting for users database identification number.
     *
     * @return users ID number
     */
    public static int getID(){
        return ID;
    }

    /**
     * Getter for windows read only width property.
     *
     * @return stages width property
     */
    public static ReadOnlyProperty widthProperty(){
        return stage.widthProperty();
    }

    /**
     * Getter for windows read only height property.
     *
     * @return stages height property
     */
    public static ReadOnlyProperty heightProperty(){
        return stage.heightProperty();
    }

}
