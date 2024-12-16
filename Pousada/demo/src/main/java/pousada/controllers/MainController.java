package pousada.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import pousada.Hospede;
import pousada.Pousada;

public class MainController {
    @FXML
    private TextField idField;
    @FXML
    private TextField canalField;
    @FXML
    private TextField ttvField;
    @FXML
    private TextField tdField;
    @FXML
    private ListView<String> hospedesList;
    @FXML
    private TextArea logArea;
    @FXML 
    private Label currentChannelLabel;

    private Pousada pousada;
    private final ObservableList<String> hospedeStatusList = FXCollections.observableArrayList();

    public void initialize() {
        pousada = new Pousada(5); // Número de canais ajustável
        hospedesList.setItems(hospedeStatusList);
    }

    public void updateCurrentChannel(int canal) {
        Platform.runLater(() -> {
            currentChannelLabel.setText("Canal Atual: " + (canal == -1 ? "Nenhum" : canal));
        });
    }

    public void updateGuestStatus(String id, String status) {
        Platform.runLater(() -> {
            for (int i = 0; i < hospedeStatusList.size(); i++) {
                if (hospedeStatusList.get(i).startsWith("Hóspede: " + id)) {
                    hospedeStatusList.set(i, "Hóspede: " + id + " - " + status);
                    return;
                }
            }
            hospedeStatusList.add("Hóspede: " + id + " - " + status);
        });
    }

    @FXML
    public void handleAdicionarHospede() {
        try {
            String id = idField.getText().trim();
            if (id.isEmpty()) {
                logArea.appendText("Erro: O ID não pode estar vazio.\n");
                return;
            }
            int canal = Integer.parseInt(canalField.getText());
            if (canal < 1 || canal > pousada.getNCanais()) {
                logArea.appendText("Erro: Canal deve ser entre 1 e " + pousada.getNCanais() + ".\n");
                return;
            }
            int ttv = Integer.parseInt(ttvField.getText());
            int td = Integer.parseInt(tdField.getText());
            if (ttv <= 0 || td <= 0) {
                logArea.appendText("Erro: Os tempos devem ser maiores que zero.\n");
                return;
            }

            Hospede hospede = new Hospede(pousada, id, canal, ttv, td, this);
            hospede.start();

            hospedesList.getItems().add("Hóspede: " + id + ", Canal: " + canal);
            logArea.appendText("Hóspede " + id + " foi adicionado.\n");

            idField.clear();
            canalField.clear();
            ttvField.clear();
            tdField.clear();
        } catch (NumberFormatException e) {
            logArea.appendText("Erro: Campos numéricos inválidos.\n");
        }
    }
}
