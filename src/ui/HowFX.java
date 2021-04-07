package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class HowFX{
    private final BorderPane rootPane;
    private final FXMLLoader fxmlLoader;

    public HowFX() {
        rootPane = new BorderPane();
        fxmlLoader = new FXMLLoader(getClass().getResource("How.fxml"));
    }

    public BorderPane getRootPane() {
        return rootPane;
    }

//    public Parent getFxmlLoader() {
//        return (Parent) fxmlLoader.load(getClass().getResource("How.fxml"));
//    }
}
