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
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);

        // Disable resizing of the window
        primaryStage.setResizable(false);

        // Display the primary stage (the main application window)
        primaryStage.show();

        // Initialize the number of channels for the Pousada
        int nCanais = 5;

        // Create a new instance of Pousada with the specified number of channels
        Pousada pousada = new Pousada(nCanais);

        // Create instances of Hospede (Guest), each with different room assignments
        Hospede h1 = new Hospede(pousada, "Hospede1", 1, 5, 2, controller);
        Hospede h2 = new Hospede(pousada, "Hospede2", 2, 4, 3, controller);
        Hospede h3 = new Hospede(pousada, "Hospede3", 1, 6, 1, controller);
        Hospede h4 = new Hospede(pousada, "Hospede4", 3, 5, 2, controller);

        // Start the guests in their respective threads
        h1.start();
        h2.start();
        h3.start();
        h4.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
