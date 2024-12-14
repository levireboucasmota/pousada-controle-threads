package pousada;

public class Main {
    public static void main(String[] args) {
        int nCanais = 5;
        Pousada pousada = new Pousada(nCanais);

        Hospede h1 = new Hospede(pousada, "Hospede1", 1, 5, 2);
        Hospede h2 = new Hospede(pousada, "Hospede2", 2, 4, 3);
        Hospede h3 = new Hospede(pousada, "Hospede3", 1, 6, 1);
        Hospede h4 = new Hospede(pousada, "Hospede4", 3, 5, 2);

        h1.start();
        h2.start();
        h3.start();
        h4.start();
    }
}
