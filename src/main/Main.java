package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    View view = new View();
    Model model = new Model(10);

    @Override
    public void start(Stage primaryStage) throws Exception{

        view.Scene1(primaryStage, model);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
