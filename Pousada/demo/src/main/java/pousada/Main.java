package pousada;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pousada.controllers.MainController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Define the path to the FXML file for the main view of the application
        System.out.println(getClass().getResource("/pousada/MainView.fxml"));
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/pousada/MainView.fxml"));

        // Load the FXML file and get the root node of the scene graph
        Parent root = loader.load();

        // Get the controller instance from the FXMLLoader
        MainController controller = loader.getController();

        // Create a new scene with the loaded root node and set it to the primary stage
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        // Set the title of the application window
        primaryStage.setTitle("Problema da televisão");

        // Set the size of the window (width and height)
        primaryStage.setWidth(1280);
        primaryStage.setHeight(720);

        // Disable resizing of the window
        primaryStage.setResizable(false);

        // Display the primary stage (the main application window)
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
