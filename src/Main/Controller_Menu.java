package Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller_Menu {
    // data fields
    public Button Btn_Play;
    public Button Btn_How;
    public Button Btn_Exit;

    // Method
    public void Change_Play(MouseEvent event) throws IOException {
        // create stage
        Parent rootHow = FXMLLoader.load(getClass().getResource("Play.fxml"));
        Scene rootHowScene = new Scene(rootHow);

        // stage
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        // display
        window.setScene(rootHowScene);
        window.show();
    }

    public void Change_How(ActionEvent event) throws IOException {
        // create stage
        Parent rootHow = FXMLLoader.load(getClass().getResource("How.fxml"));
        Scene rootHowScene = new Scene(rootHow);

        // stage
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        // display
        window.setScene(rootHowScene);
        window.show();
    }

    public void Change_Exit(ActionEvent event) {
        // stage
        Stage stage = (Stage) Btn_Exit.getScene().getWindow();
        stage.close();
    }
}
