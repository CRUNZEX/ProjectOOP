package UI.Menu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller_Menu {
    // data fields
    public Button Btn_Play;
    public Button Btn_How;
    public Button Btn_Exit;
    public Group group = new Group();

    // Method
    public void Change_Play(MouseEvent event) throws IOException {
        // create stage
        Parent rootHow = FXMLLoader.load(getClass().getResource("../Play/Play.fxml"));
        group.getChildren().add(rootHow);
        Scene rootHowScene = new Scene(group);

        // stage
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        // display
        window.setScene(rootHowScene);
        window.show();
    }

    public void Change_How(MouseEvent event) throws IOException {
        // create stage
        Parent rootHow = FXMLLoader.load(getClass().getResource("../How/How.fxml"));
        Scene rootHowScene = new Scene(rootHow);

        // stage
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        // display
        window.setScene(rootHowScene);
        window.show();
    }

    public void Change_Exit(MouseEvent event) {
        // stage
        Stage stage = (Stage) Btn_Exit.getScene().getWindow();
        stage.close();
    }
}
