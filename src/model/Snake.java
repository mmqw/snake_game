package model;
import javafx.scene.paint.Color;
import view.GameView;

import java.util.ArrayList;


public class Snake {

    /** Snake body object **/
    public static class BodyPart {
        int x;
        int y;

        public BodyPart(int x, int y) {
            super();
            this.x = x;
            this.y = y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

    }

    /** Snake object **/
    static final int WIDTH = GameView.CANVAS_WIDTH;
    static final int HEIGHT = GameView.CANVAS_HEIGHT;
    private static final int INITIAL_SIZE = 4;
    /** how big the actual thingy is **/
    public static final int SIZE = 10;


    // Array that holds the entire body
    private ArrayList<BodyPart> body;
    private BodyPart head;

    public static final Color HEAD_COLOR = Color.ROYALBLUE;
    public static final Color BODY_COLOR = Color.SKYBLUE;


    //Snake's size variable and starting position of head
    private int size, headX = WIDTH/2+SIZE, headY = 400+SIZE;

    /** Snake constructor **/
    public Snake() {
        body = new ArrayList<>();
        head = new BodyPart(headX, headY);
        size = 0;
        setStart();
    }

    /** Set snake at starting position **/
    public void setStart() {

        // set starting position
        if(size == 0) {
            body.add(head);
            ++size;

            for(int i = 1; i < INITIAL_SIZE; ++i) {
                addBodyPart(headX, headY+(i * SIZE*2));
            }
        }
        //if game restart
        else {
            body.clear();
            head.setX(headX);
            head.setY(headY);
            size = 0;
            setStart();
        }
    }

    public void addBodyPart(int x, int y) {
        body.add(new BodyPart(x,y));
        ++size;
    }

    /** Return snake's actual size **/
    public int getSize() {
        return this.size;
    }

    /** Return specified body part **/
    public BodyPart getBodyPart(int i) {
        return body.get(i);
    }

    /** Returns the head of the snake as BodyPart object **/
    public BodyPart getHead() {
        return this.head;
    }
}
