package Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller_How {
    // data fields
    public Button Btn_Back;

    // method
    public void Change_Back(ActionEvent event) throws IOException {
        Parent rootHow = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Scene rootHowScene = new Scene(rootHow);

        // stage
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        // display
        window.setScene(rootHowScene);
        window.show();
    }
}
