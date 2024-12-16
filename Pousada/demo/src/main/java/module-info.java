module pousada {
    requires javafx.controls;
    requires javafx.fxml;

    opens pousada to javafx.fxml;
    exports pousada;
    opens pousada.controllers to javafx.fxml;

}
