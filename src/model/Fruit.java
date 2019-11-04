package model;
import javafx.scene.paint.Color;


public class Fruit {
    public static final Color FRUIT_COLOR = Color.THISTLE;
    int x;
    int y;

    public Fruit(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
