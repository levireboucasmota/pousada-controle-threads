package pousada;
import java.util.concurrent.Semaphore;

class Pousada {
    private final Semaphore controleRemoto;
    private Semaphore mutex;
    private int canalAtual;
    private int espectadoresAtuais;

    public Pousada(int nCanais) {
        this.controleRemoto = new Semaphore(1, true);
        this.mutex = new Semaphore(1, true);
        this.canalAtual = -1;
        this.espectadoresAtuais = 0;
    }

    public void assistirTv(int canal, String id) throws InterruptedException {
        while (true) {
            controleRemoto.acquire();
            mutex.acquire();
            try {
                if (canalAtual == -1 || canalAtual == canal) {
                    canalAtual = canal;
                    espectadoresAtuais++;
                    System.out.println(id + " está assistindo ao canal " + canal);
                    break;
                }
            } finally {
                mutex.release();
                controleRemoto.release();
            }
            Thread.sleep(1000); // Espera um pouco antes de tentar novamente
        }
    }

    public void liberarTv(int canal, String id) throws InterruptedException {
        mutex.acquire();
        try {
            espectadoresAtuais--;
            System.out.println(id + " liberou o canal " + canal);
            if (espectadoresAtuais == 0) {
                canalAtual = -1;
                controleRemoto.release();
            }
        } finally {
            mutex.release();
        }
    }
}