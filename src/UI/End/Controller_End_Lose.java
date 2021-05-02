package UI.End;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller_End_Lose {
    public Label Label_Result = new Label();
    public Button Btn_PlayAgain;
    public Button Btn_Exit;
    public Group group = new Group();

    public void Change_PlayAgain(MouseEvent mouseEvent) throws IOException {
        // create stage
        Parent rootHow = FXMLLoader.load(getClass().getResource("../Play/Play.fxml"));
        group.getChildren().add(rootHow);
        Scene rootPlayScene = new Scene(group);

        // stage
        Stage window = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
    
        // display
        window.setScene(rootPlayScene);
        window.show();
    }

    public void Change_Exit(MouseEvent mouseEvent) {
        // stage
        Stage stage = (Stage) Btn_Exit.getScene().getWindow();
        stage.close();
    }
}
