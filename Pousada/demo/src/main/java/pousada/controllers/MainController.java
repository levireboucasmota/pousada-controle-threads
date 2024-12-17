package pousada.controllers;

import javafx.scene.text.Text;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import pousada.Hospede;
import pousada.Pousada;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class MainController {
    @FXML
    private HBox televisionContent;

    @FXML
    private TextField idField;
    @FXML
    private ComboBox canalField;
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

    @FXML
    private VBox bonecosContainer;

    private Pousada pousada;
    private final ObservableList<String> hospedeStatusList = FXCollections.observableArrayList();
    private final List<HospedeStatus> hospedes = new ArrayList<>();

    public void initialize() {
        pousada = new Pousada(5, this);
        hospedesList.setItems(hospedeStatusList);
    }

    public void updateCurrentChannel(int canal) {
        Platform.runLater(() -> {
            currentChannelLabel.setText("Canal Atual: " + (canal == -1 ? "Nenhum" : canal));
            updateImage(canal);
        });
    }

    public void updateGuestStatus(String id, String status) {
        Platform.runLater(() -> {
            Optional<HospedeStatus> hospedeExistente = hospedes.stream()
                    .filter(h -> h.getId().equals(id))
                    .findFirst();

            if (hospedeExistente.isPresent()) {
                hospedeExistente.get().setStatus(status);
            } else {
                hospedes.add(new HospedeStatus(id, status));
            }
            atualizarBonecos();
            logArea.appendText("Hóspede " + id + ": " + status + "\n");
        });
    }

    private void atualizarBonecos() {
        Platform.runLater(() -> {
            bonecosContainer.getChildren().clear();
            for (HospedeStatus hospede : hospedes) {
                Pane boneco = criarBoneco(hospede.getStatus());
                Label label = new Label("Hóspede " + hospede.getId());
                label.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
                VBox bonecoComLabel = new VBox(boneco, label);
                bonecoComLabel.setAlignment(Pos.CENTER);
                bonecosContainer.getChildren().add(bonecoComLabel);
            }
        });
    }

    private Pane criarBoneco(String status) {
        Pane pane = new Pane();
    
        // Cabeça
        Circle head = new Circle(10);
        head.setCenterX(20);
        head.setCenterY(10);
    
        // Corpo
        Line body = new Line(20, 20, 20, 50);
    
        // Braços
        Line leftArm = new Line(20, 30, 5, 40);
        Line rightArm = new Line(20, 30, 35, 40);
    
        // Pernas
        Line leftLeg = new Line(20, 50, 5, 70);
        Line rightLeg = new Line(20, 50, 35, 70);
    
        // Define cor com base no status
        if (status.contains("assistindo")) {
            head.setFill(Color.BLUE); // Azul para assistindo
        } else if (status.contains("descansando")) {
            head.setFill(Color.RED); // Vermelho para descansando
    
            // Adiciona balão de fala com "zzzz"
            Text zText = new Text("zzzz");
            zText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            zText.setFill(Color.GRAY);
    
            // Posiciona o texto acima da cabeça do boneco
            zText.setX(head.getCenterX() + 10);
            zText.setY(head.getCenterY() - 10);
    
            pane.getChildren().add(zText);
        } else {
            head.setFill(Color.GRAY); // Cor padrão para outros status
        }
    
        // Adiciona os componentes ao pane
        pane.getChildren().addAll(head, body, leftArm, rightArm, leftLeg, rightLeg);
        pane.setPrefSize(40, 80);
    
        return pane;
    }

    public void updateImage(int canalAtual) {
        Platform.runLater(() -> {
            televisionContent.getChildren().clear();
            String imagePath = "/assets/" + canalAtual + ".jpg";
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(360);
            imageView.setFitHeight(210);
            televisionContent.getChildren().add(imageView);
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

            String canalSelecionado = canalField.getValue();
            String[] partes = canalSelecionado.split("- ");
            int canal = Integer.parseInt(partes[1].trim());
            if (canal == null || canal < 1 || canal > pousada.getNCanais()) {
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

    private static class HospedeStatus {
        private final String id;
        private String status;

        public HospedeStatus(String id, String status) {
            this.id = id;
            this.status = status;
        }

        public String getId() {
            return id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}