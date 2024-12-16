package pousada;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Main class for the Pousada application. This class extends the JavaFX {@link Application} 
 * and sets up the main application window, loads the FXML view, and initializes the environment 
 * for guests with a specified number of channels.
 * 
 * The application creates several guests (instances of {@link Hospede}), each associated with 
 * a room in the Pousada and starts them on separate threads.
 */
public class Main extends Application {

    /**
     * The main entry point for the JavaFX application. This method is called when the application 
     * is launched. It sets up the primary stage, loads the FXML view, and initializes the guests.
     *
     * @param primaryStage The main stage of the JavaFX application.
     * @throws Exception If there is an error loading the FXML or initializing the application.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Define the path to the FXML file for the main view of the application
        String fxmlPath = "/pousada/MainView.fxml";

        // Create the FXMLLoader to load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

        // Load the FXML file and get the root node of the scene graph
        Parent root = loader.load();

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
        Hospede h1 = new Hospede(pousada, "Hospede1", 1, 5, 2);
        Hospede h2 = new Hospede(pousada, "Hospede2", 2, 4, 3);
        Hospede h3 = new Hospede(pousada, "Hospede3", 1, 6, 1);
        Hospede h4 = new Hospede(pousada, "Hospede4", 3, 5, 2);

        // Start the guests in their respective threads
        h1.start();
        h2.start();
        h3.start();
        h4.start();

        // Update the title of the stage (optional, as the title is already set above)
        primaryStage.setTitle("Pousada");

        // Show the primary stage (repeated here as a safeguard)
        primaryStage.show();
    }

    /**
     * The main method that launches the JavaFX application.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        launch(args);
    }
}
