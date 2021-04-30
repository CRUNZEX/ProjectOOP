package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../UI/Menu/Menu.fxml")));
        primaryStage.setTitle("Old maid");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root,1280,720));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
