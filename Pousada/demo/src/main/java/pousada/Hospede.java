package pousada;
class Hospede extends Thread {
    private final Pousada pousada;
    private final String id;
    private final int canalFavorito;
    private final int ttv; // Tempo assistindo TV
    private final int td; // Tempo descansando

    public Hospede(Pousada pousada, String id, int canalFavorito, int ttv, int td) {
        this.pousada = pousada;
        this.id = id;
        this.canalFavorito = canalFavorito;
        this.ttv = ttv;
        this.td = td;
    }

    @Override
    public void run() {
        try {
            while (true) {
                pousada.assistirTv(canalFavorito, id);
                System.out.println(id + " está processando enquanto assiste ao canal " + canalFavorito);
                Thread.sleep(ttv * 1000); // Assiste por ttv segundos
                pousada.liberarTv(canalFavorito, id);
                System.out.println(id + " está processando enquanto descansa");
                Thread.sleep(td * 1000); // Descansa por td segundos
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}