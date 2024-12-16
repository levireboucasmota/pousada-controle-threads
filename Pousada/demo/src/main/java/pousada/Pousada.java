package pousada;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Pousada {
    private final Semaphore controleRemoto;
    private final Lock lock;
    private int canalAtual;
    private int espectadoresAtuais;
    private final int nCanais;

    public Pousada(int nCanais) {
        this.nCanais = nCanais;
        this.controleRemoto = new Semaphore(1, true);
        this.lock = new ReentrantLock();
        this.canalAtual = -1;
        this.espectadoresAtuais = 0;
    }

    public int getNCanais() {
        return nCanais;
    }

    public void assistirTv(int canal, String id) throws InterruptedException {
        while (true) {
            controleRemoto.acquire();
            lock.lock();
            try {
                if (canalAtual == -1 || canalAtual == canal) {
                    canalAtual = canal;
                    espectadoresAtuais++;
                    System.out.println(id + " está assistindo ao canal " + canal);
                    break;
                }
            } finally {
                lock.unlock();
                controleRemoto.release();
            }
            Thread.sleep(100); // Espera um pouco antes de tentar novamente
        }
    }

    public void liberarTv(int canal, String id) {
        lock.lock();
        try {
            espectadoresAtuais--;
            System.out.println(id + " liberou o canal " + canal);
            if (espectadoresAtuais == 0) {
                canalAtual = -1;
                controleRemoto.release();
            }
        } finally {
            lock.unlock();
        }
    }
}
