package view;

import controller.GameController;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.shape.Polygon;
import model.Level;


public class InfoBar {
    private StackPane stack;
    private Label l1, l2, l3, l4, levelLabel, level, s, timeLabel, timer;

    public static final int INFO_HEIGHT = 60;

    private final Color scoreColor = Color.THISTLE;

    private final Color scoreFieldColor = Color.SLATEBLUE;


    public InfoBar() {
        stack = new StackPane();
        Rectangle r = new Rectangle(GameView.WIDTH, INFO_HEIGHT);
        r.setFill(scoreFieldColor);

        // triangles on the sides
        double x = (double)1280;
        double y = (double)INFO_HEIGHT;
        double z = (double)1280;

        // setting the labels
        l1 = new Label("SCORE: ");
        l1.setFont(new Font(25));
        l1.setTextFill(scoreColor);
        l2 = new Label("0");
        l2.setFont(new Font(25));
        l2.setTextFill(scoreColor);

        l3 = new Label("Press P to Pause");
        l3.setFont(new Font(13));
        l3.setTextFill(scoreColor);


        l4 = new Label("Press Q to End Game");
        l4.setFont(new Font(13));
        l4.setTextFill(scoreColor);

        levelLabel = new Label("Level: ");
        levelLabel.setFont(new Font(25));
        levelLabel.setTextFill(scoreColor);
        level = new Label("1");
        level.setFont(new Font(25));
        level.setTextFill(scoreColor);

        timeLabel = new Label("Time Left: ");
        timeLabel.setFont(new Font(25));
        timeLabel.setTextFill(scoreColor);
        timer = new Label("30");
        timer.setFont(new Font(25));
        timer.setTextFill(scoreColor);

        s = new Label("s");
        s.setFont(new Font(25));
        s.setTextFill(scoreColor);

        stack.getChildren().addAll(r,l1, l2, l3, l4, timeLabel, timer, s, levelLabel, level);
        stack.getChildren().get(1).setTranslateX(-30);
        stack.getChildren().get(2).setTranslateX(40);
        stack.getChildren().get(3).setTranslateX(150);
        stack.getChildren().get(3).setTranslateY(15);
        stack.getChildren().get(4).setTranslateX(-170);
        stack.getChildren().get(4).setTranslateY(15);
        stack.getChildren().get(5).setTranslateX(450);
        stack.getChildren().get(6).setTranslateX(530);
        stack.getChildren().get(7).setTranslateX(555);
        stack.getChildren().get(8).setTranslateX(-500);
        stack.getChildren().get(9).setTranslateX(-450);


    }

    public void updateScore(int score) {
        l2.setText(Integer.toString(score));
    }

    public void updateTimer(int time) {
        if (GameController.getLevel() == Level.THREE) {
            timer.setText("--");
        } else {
            timer.setText(Integer.toString(31 - time));
        }
    }

    public void updateLevel(Level lev) {
        if (lev == Level.ONE) level.setText("1");
        if (lev == Level.TWO) level.setText("2");
        if (lev == Level.THREE) level.setText("3");
    }

    public StackPane getStack() {
        return stack;
    }

}
