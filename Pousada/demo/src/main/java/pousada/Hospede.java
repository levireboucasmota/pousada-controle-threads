package pousada;

import pousada.controllers.MainController;

public class Hospede extends Thread {
    private final Pousada pousada;
    private final String id;
    private final int canalFavorito;
    private final int ttv; // Tempo assistindo TV
    private final int td; // Tempo descansando
    private final MainController controller;

    public Hospede(Pousada pousada, String id, int canalFavorito, int ttv, int td, MainController controller) {
        this.pousada = pousada;
        this.id = id;
        this.canalFavorito = canalFavorito;
        this.ttv = ttv;
        this.td = td;
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            while (true) {
                pousada.assistirTv(canalFavorito, id);
                controller.updateGuestStatus(id, "assistindo TV no canal " + canalFavorito);
                Utils.timeCpuBound(ttv, () -> {
                    controller.updateGuestStatus(id, "assistindo TV no canal " + canalFavorito);
                });

                pousada.liberarTv(canalFavorito, id);
                controller.updateGuestStatus(id, "descansando");
                Utils.timeCpuBound(td, () -> {
                    controller.updateGuestStatus(id, "descansando");
                });
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
