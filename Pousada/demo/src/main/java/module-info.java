module pousada {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens pousada to javafx.fxml;
    exports pousada;
    exports pousada.controllers;
    opens pousada.controllers to javafx.fxml;

}
