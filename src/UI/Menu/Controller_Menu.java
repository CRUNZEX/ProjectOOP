package UI.Menu;

import UI.Play.Controller_Play;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
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
    public Group group = new Group();
    public ImageView PlayAction;
    public ImageView HowAction;
    public ImageView ExitAction;

    // Method
    public void Change_Play(MouseEvent event) throws IOException {
        // create stage
        Parent rootHow = FXMLLoader.load(getClass().getResource("../Play/Play.fxml"));
        group.getChildren().add(rootHow);
        Scene rootPlayScene = new Scene(group);

        // stage
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        // display
        window.setScene(rootPlayScene);
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

    public void Play_Exited(MouseEvent event) {
        Image play0 = new Image("/Image/play0.png");
        PlayAction.setImage(play0);
    }

    public void Play_Enter(MouseEvent event) {
        Image play1 = new Image("/Image/play1.png");
        PlayAction.setImage(play1);
    }

    public void How_Exited(MouseEvent event) {
        Image how0 = new Image("/Image/Howtoplay0.png");
        HowAction.setImage(how0);
    }

    public void How_Enter(MouseEvent event) {
        Image how1 = new Image("/Image/Howtoplay1.png");
        HowAction.setImage(how1);
    }

    public void Exit_Exited(MouseEvent event) {
        Image exit0 = new Image("/Image/Exit0.png");
        ExitAction.setImage(exit0);
    }

    public void Exit_Enter(MouseEvent event) {
        Image exit1 = new Image("/Image/Exit1.png");
        ExitAction.setImage(exit1);
    }
}
