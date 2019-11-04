package model;

import controller.GameController;
import view.InfoBar;

import java.util.ArrayList;
import java.util.Random;

public class Board {
    private int score, highscore, fruitsEaten;
    private Snake snake;
    private Snake.BodyPart head;

    private Level level;

    private ArrayList<Fruit> fruits = new ArrayList<>();

    private GameState state;

    private GameController gameCtrl;

    private InfoBar infoBar;

    Random rand = new Random();

    ArrayList<Fruit> level1Fruits;
    ArrayList<Fruit> level2Fruits;
    ArrayList<Fruit> level3Fruits;

    /** Object of ScoreView to exchange informations about actual score */
    /** TODO: private ScoreView scoreView; **/

    public Board(GameController gameCtrl) {

        this.gameCtrl = gameCtrl;
        infoBar = new InfoBar();
        score = highscore = fruitsEaten = 0;
        snake = new Snake();
        head = snake.getHead();
        state = GameState.RUNNING;
        level = GameController.getLevel(); // get game level from game controller

        level1Fruits = setLevel1Fruits();
        level2Fruits = setLevel2Fruits();
        level3Fruits = setLevel3Fruits();

        if (level == Level.ONE) {
            fruits = level1Fruits;
        }
        else if (level == Level.TWO) {
            fruits = level2Fruits;
        }
        else if (level == Level.THREE) {
            fruits.clear();
        }
    }

    public boolean bodyOnFruit() {
        int bodyX, bodyY, fruitX, fruitY;
        for(int i = 1; i < snake.getSize(); i++) {
            bodyX = snake.getBodyPart(i).getX();
            bodyY = snake.getBodyPart(i).getY();
            for (int k = 0; k < fruits.size(); k++) {
                fruitX = fruits.get(k).getX();
                fruitY = fruits.get(k).getY();

                if(bodyX == fruitX && bodyY == fruitY) {
                    removeFruit(k);
                    addSnakeLength();
                    fruitsEaten++;
                    score++;
                    highscore = score;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean eatFruit() {
        int headX, headY, fruitX, fruitY;
        headX = snake.getHead().getX();
        headY = snake.getHead().getY();

        for (int i = 0; i < fruits.size(); i++) {
            fruitX = fruits.get(i).getX();
            fruitY = fruits.get(i).getY();

            if(headX == fruitX && headY == fruitY) {
                removeFruit(i);
                addSnakeLength();
                fruitsEaten++;
                score++;
                highscore = score;
                return true;
            }
        }
        return false;
    }

    public GameState eatSelf() {
        int headX, headY;
        headX = snake.getHead().getX();
        headY = snake.getHead().getY();

        int bodyX, bodyY;
        for(int i = 1; i < snake.getSize(); i++) {
            bodyX = snake.getBodyPart(i).getX();
            bodyY = snake.getBodyPart(i).getY();

            if(headX == bodyX && headY == bodyY) {
                highscore = score;
                reset();
                return GameState.FINISHED;
            }
        }
        return GameController.getState();
    }

    /** Update the "fruits" list depending on level...... **/
    public void updateFruit() {
        // Generate new random fruits after the fixed ones have been consumed
        if(fruits.isEmpty()) {
            int foodX, foodY;
            foodX = (rand.nextInt(63)*Snake.SIZE*2)+Snake.SIZE;
            foodY = (rand.nextInt(37)*Snake.SIZE*2)+Snake.SIZE;
            Fruit fruit = new Fruit(foodX, foodY);
            fruits.add(fruit);
        }
    }

    public void updateHighScore() {
        highscore = score;
    }

    public void updateFruitsLevelOnUser(Level level, int seconds) {
        if (level == Level.ONE && seconds == 1) {
            fruits.clear();
            level1Fruits = setLevel1Fruits();
            fruits.addAll(level1Fruits);
        }
        else if (level == Level.TWO && seconds == 1) {
            fruits.clear();
            level2Fruits = setLevel2Fruits();
            fruits.addAll(level2Fruits);
        }
        else if (level == Level.THREE && seconds == 1) {
            fruits.clear();
        }
    }

    public void updateFruitsLevelOnCompletion (Level level) {
        if (level == Level.ONE) { // move onto level 2
            fruits.clear();
            level2Fruits = setLevel2Fruits();
            fruits.addAll(level2Fruits);
        }
        else if (level == Level.TWO) { // move onto level 3
            fruits.clear();
            level3Fruits = setLevel3Fruits();
            fruits.addAll(level3Fruits);
        }
    }

    public void removeFruit(int i) {
        fruits.remove(i);
    }

    public void addSnakeLength() {
        // CHECKING direction won't work because if body just turned, will add weird
        // Need to get the last 2 body parts
        Snake.BodyPart last1 = snake.getBodyPart(snake.getSize()-1);
        Snake.BodyPart last2 = snake.getBodyPart(snake.getSize()-2);

        // Snake body travelling left
        if(last1.getX() < last2.getX()) {
            snake.addBodyPart(last1.getX()+Snake.SIZE*2, last1.getY());
        }
        // Snake body travelling right
        if(last1.getX() > last2.getX()) {
            snake.addBodyPart(last1.getX()-Snake.SIZE*2, last1.getY());
        }
        // Snake body travelling up
        if(last1.getY() < last2.getY()) {
            snake.addBodyPart(last1.getX(), last1.getY()-Snake.SIZE*2);
        }
        // Snake body travelling down
        if(last1.getY() > last2.getY()) {
            snake.addBodyPart(last1.getX(), last1.getY()+Snake.SIZE*2);
        }
    }

    public Snake getSnake() {
        return snake;
    }

    public ArrayList<Fruit> setLevel1Fruits() {
        ArrayList<Fruit> fruitList = new ArrayList<>();
        Fruit fruit1 = new Fruit(10*Snake.SIZE*2+Snake.SIZE, 15*Snake.SIZE*2+Snake.SIZE);
        Fruit fruit2 = new Fruit(20*Snake.SIZE*2+Snake.SIZE, 20*Snake.SIZE*2+Snake.SIZE);
        Fruit fruit3 = new Fruit(15*Snake.SIZE*2+Snake.SIZE, 28*Snake.SIZE*2+Snake.SIZE);
        Fruit fruit4 = new Fruit(50*Snake.SIZE*2+Snake.SIZE, 16*Snake.SIZE*2+Snake.SIZE);
        Fruit fruit5 = new Fruit(2*Snake.SIZE*2+Snake.SIZE, 20*Snake.SIZE*2+Snake.SIZE);
        fruitList.add(fruit1);
        fruitList.add(fruit2);
        fruitList.add(fruit3);
        fruitList.add(fruit4);
        fruitList.add(fruit5);
        return fruitList;
    }

    public ArrayList<Fruit> setLevel2Fruits() {
        ArrayList<Fruit> fruitList = new ArrayList<>();
        Fruit fruit1 = new Fruit(10*Snake.SIZE*2+Snake.SIZE, 35*Snake.SIZE*2+Snake.SIZE);
        Fruit fruit2 = new Fruit(62*Snake.SIZE*2+Snake.SIZE, 28*Snake.SIZE*2+Snake.SIZE);
        Fruit fruit3 = new Fruit(15*Snake.SIZE*2+Snake.SIZE, 8*Snake.SIZE*2+Snake.SIZE);
        Fruit fruit4 = new Fruit(45*Snake.SIZE*2+Snake.SIZE, 25*Snake.SIZE*2+Snake.SIZE);
        Fruit fruit5 = new Fruit(29*Snake.SIZE*2+Snake.SIZE, 2*Snake.SIZE*2+Snake.SIZE);
        Fruit fruit6 = new Fruit(59*Snake.SIZE*2+Snake.SIZE, 35*Snake.SIZE*2+Snake.SIZE);
        Fruit fruit7 = new Fruit(28*Snake.SIZE*2+Snake.SIZE, 22*Snake.SIZE*2+Snake.SIZE);
        Fruit fruit8 = new Fruit(15*Snake.SIZE*2+Snake.SIZE, 8*Snake.SIZE*2+Snake.SIZE);
        Fruit fruit9 = new Fruit(Snake.SIZE*2+Snake.SIZE, Snake.SIZE*2+Snake.SIZE);
        Fruit fruit10 = new Fruit(10*Snake.SIZE*2+Snake.SIZE, 31*Snake.SIZE*2+Snake.SIZE);
        Fruit fruit11 = new Fruit(50*Snake.SIZE*2+Snake.SIZE, 14*Snake.SIZE*2+Snake.SIZE);
        Fruit fruit12 = new Fruit(20*Snake.SIZE*2+Snake.SIZE, 20*Snake.SIZE*2+Snake.SIZE);
        fruitList.add(fruit1);
        fruitList.add(fruit2);
        fruitList.add(fruit3);
        fruitList.add(fruit4);
        fruitList.add(fruit5);
        fruitList.add(fruit6);
        fruitList.add(fruit7);
        fruitList.add(fruit8);
        fruitList.add(fruit9);
        fruitList.add(fruit10);
        fruitList.add(fruit11);
        fruitList.add(fruit12);
        return fruitList;
    }

    public ArrayList<Fruit> setLevel3Fruits() {
        ArrayList<Fruit> fruitList = new ArrayList<>();
        return fruitList;
    }

    public ArrayList<Fruit> getFruits() {
        return fruits;
    }

    public Level getLevel() {
        return level;
    }

    public void updateScore() {
        infoBar.updateScore(score);
    }

    public void updateTimer(int time) {
        infoBar.updateTimer(time);
    }

    public boolean updateLevel(Level nextLevel) {
        if (level == nextLevel) return false;
        else {
            level = nextLevel;
            infoBar.updateLevel(nextLevel);
            return true;
        }
    }

    public InfoBar getInfoBar() {
        return infoBar;
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highscore;
    }

    /**
     * Resets basic values of the game after lose
     */
    private void reset() {
        snake.setStart();
        fruits.clear();
        score = fruitsEaten = 0;
    }
}
