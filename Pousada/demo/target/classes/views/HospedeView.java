package pousada.views;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class HospedeView extends Circle {
    public HospedeView(double x, double y) {
        super(10, Color.BLUE);
        setLayoutX(x);
        setLayoutY(y);
    }

    public void move(double dx) {
        setLayoutX(getLayoutX() + dx);
    }
}
