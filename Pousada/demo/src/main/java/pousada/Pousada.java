package pousada;

import java.util.concurrent.Semaphore;

import javafx.application.Platform;
import pousada.controllers.MainController;

public class Pousada {
    private final Semaphore controleRemoto;
    private final Semaphore mutex;
    private final Semaphore espera;
    private int canalAtual;
    private int espectadoresAtuais;
    private final int nCanais;
    private MainController mainController;

    public Pousada(int nCanais, MainController mainController) {
        this.nCanais = nCanais;
        this.controleRemoto = new Semaphore(1, true);
        this.mutex = new Semaphore(1, true);
        this.espera = new Semaphore(0, true); 
        this.canalAtual = -1;
        this.espectadoresAtuais = 0;
        this.mainController = mainController;
    }

    public int getNCanais() {
        return nCanais;
    }

    public void assistirTv(int canal, String id) throws InterruptedException {
        controleRemoto.acquire();
        while (true) {
            mutex.acquire();
            try {
                if (canalAtual == -1 || canalAtual == canal) {
                    canalAtual = canal;
                    Platform.runLater(() -> {
                        mainController.updateImage(canalAtual);
                    });
                    espectadoresAtuais++;
                    System.out.println(id + " está assistindo ao canal " + canal);
                    break;
                }
            } finally {
                mutex.release(); 
            }
            espera.acquire();
        }
        controleRemoto.release();
    }

    public void liberarTv(int canal, String id) throws InterruptedException {
        mutex.acquire();
        try {
            espectadoresAtuais--;
            System.out.println(id + " liberou o canal " + canal);
            if (espectadoresAtuais == 0) {
                canalAtual = -1;
                Platform.runLater(() -> {
                    mainController.clearImage();
                });
            espera.release();
            }
        } finally {
            mutex.release();
        }
    }
}
