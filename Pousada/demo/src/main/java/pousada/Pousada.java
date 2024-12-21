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
    private int espectadoresEsperando;
    private final int nCanais;
    private MainController mainController;

    public Pousada(int nCanais, MainController mainController) {
        this.nCanais = nCanais;
        this.controleRemoto = new Semaphore(1, true);
        this.mutex = new Semaphore(1, true);
        this.espera = new Semaphore(0, true); 
        this.canalAtual = -1;
        this.espectadoresAtuais = 0;
        this.espectadoresEsperando = 0;
        this.mainController = mainController;
    }

    public int getNCanais() {
        return nCanais;
    }

    public void assistirTv(int canal, String id) throws InterruptedException {
        while (true) {
            mutex.acquire();
            try {
                if (canalAtual == -1 || canalAtual == canal) {
                    if (canalAtual == -1) {
                        controleRemoto.acquire();
                        canalAtual = canal;
                        Platform.runLater(() -> {
                            mainController.updateImage(canalAtual);
                        });
                        controleRemoto.release();
                    }
    
                    espectadoresAtuais++;
                    System.out.println(id + " está assistindo ao canal " + canal);
                    break; 
                }
            } finally {
                mutex.release();
            }
            espectadoresEsperando++;
            espera.acquire();
        }
    }
    
    public void liberarTv(int canal, String id) throws InterruptedException {
        mutex.acquire();
        try {
            espectadoresAtuais--;
            System.out.println(id + " liberou o canal " + canal);
            if (espectadoresAtuais == 0) {
            
                controleRemoto.acquire();
                canalAtual = -1;
                Platform.runLater(() -> {
                    mainController.clearImage();
                });
                controleRemoto.release();
           
                    espera.release(espectadoresEsperando);
            }
        } finally {
            mutex.release();
        }
    }
}