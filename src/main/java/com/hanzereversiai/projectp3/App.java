package com.hanzereversiai.projectp3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/connection-panel.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.setTitle("Tournament framework");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
