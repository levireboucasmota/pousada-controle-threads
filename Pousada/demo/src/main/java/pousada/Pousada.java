package pousada;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Pousada {
    private final Semaphore controleRemoto;
    private final Lock lock;
    private int canalAtual;
    private int espectadoresAtuais;

    public Pousada(int nCanais) {
        this.controleRemoto = new Semaphore(1, true);
        this.lock = new ReentrantLock();
        this.canalAtual = -1;
        this.espectadoresAtuais = 0;
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
            Thread.sleep(1000); // Espera um pouco antes de tentar novamente
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