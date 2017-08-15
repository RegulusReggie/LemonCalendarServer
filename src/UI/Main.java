package UI;

import Util.Commons;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Connection.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Server.main();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
