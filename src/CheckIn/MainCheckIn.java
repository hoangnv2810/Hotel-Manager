package CheckIn;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainCheckIn extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
//        comBoxMDV.setEditable(true);
//        comBoxMP.setEditable(true);
//        comBoxMDV.setVisibleRowCount(5);
//        comBoxMP.setVisibleRowCount(5);
        Parent root = FXMLLoader.load(getClass().getResource("CheckIn.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
