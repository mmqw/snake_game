package controller;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import view.GameView;

import java.net.URISyntaxException;


public class GameController {
    private static Stage primaryStage;

    // The actual state of the game
    public static GameState state;

    static Level level;

    // Controls of the game
    private boolean up, down, right, left;

    public enum Dir {
        LEFT, RIGHT, UP, DOWN
    }

    // Direction the snake moved
    private int posX, posY;

    // Speed of the snake
    private int speed = 5;

    private Snake snake;
    private Snake.BodyPart head;

    private final GameView gameView;
    private Board board;

    private Dir direction = Dir.UP;

    private static int seconds = 0;

    private boolean registerKey;

    private static Sound sounds;
    AudioClip biteFX;
    AudioClip levelUpFX;
    AudioClip endGameFX;

    MediaPlayer bkMusic;


    public GameController(Stage primaryStage, Level level) {
        GameController.primaryStage = primaryStage;
        GameController.level = level;
        state = GameState.RUNNING;
        this.gameView = new GameView(this);

        snake = gameView.getSnake();
        head = gameView.getSnake().getHead();
        board = gameView.getBoard();

        up = true;
        right = left = down = false;

        registerKey = true;

        try {
            sounds = new Sound();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        bkMusic = sounds.getBackgroundAudio();
        bkMusic.setOnEndOfMedia(new Runnable() {
            public void run() {
                bkMusic.seek(Duration.ZERO);
            }
        });
        bkMusic.play();
        biteFX = sounds.getBiteSound();
        levelUpFX = sounds.getLevelUpSound();
        endGameFX = sounds.getEndGameSound();


        if (level == Level.ONE) {
            speed = 5;
        }
        if (level == Level.TWO) {
            speed = 10;
        }

        if (level == Level.THREE) {
            speed = 15;
        }
        startGame();
    }

    private void userMoves(Scene scene) {

        scene.setOnKeyPressed(key -> {
            /** GET THE USER INPUT **/
            if (!down && key.getCode() == KeyCode.UP && registerKey) {
                direction = Dir.UP;
                up = true; down = left = right = false;
                registerKey = false;
            }
            else if (!right && key.getCode() == KeyCode.LEFT && registerKey) {
                direction = Dir.LEFT;
                left = true; up = down = right = false;
                registerKey = false;
            }
            else if (!up && key.getCode() == KeyCode.DOWN) {
                direction = Dir.DOWN;
                down = true; up = left = right = false;
                registerKey = false;
            }
            else if (!left && key.getCode() == KeyCode.RIGHT) {
                direction = Dir.RIGHT;
                right = true; down = up = left = false;
                registerKey = false;
            }
            else if (key.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            }

            /** CHECK OTHER USER ACTIONSs **/
            // PAUSE GAME
            if (key.getCode() == KeyCode.P && state == GameState.RUNNING) {
                // pause game
                state = GameState.PAUSED;
            }
            // RESUME GAME
            else if (key.getCode() == KeyCode.P && state == GameState.PAUSED) {
                // pause game
                state = GameState.RUNNING;
                startGame();
            }
            // IF DEAD, CAN START AGAIN
            if (key.getCode() == KeyCode.R && state == GameState.FINISHED) {
                state = GameState.RESET;
                gameView.runGame();
                resetGame();
            }
            else if (key.getCode() == KeyCode.R && state == GameState.PAUSED) {
                state = GameState.RESET;
                gameView.runGame();
                resetGame();
            }
            else if (key.getCode() == KeyCode.R) {
                // reset the game to home screen
                System.out.println("TRYING TO RESET");
                state = GameState.RESET;
                resetGame();
            }
            // Can only quit when game is running
            if (key.getCode() == KeyCode.Q && state == GameState.RUNNING) {
                // stop game and display finish screen
                state = GameState.FINISHED;
            }
            // need this because timer/game loop has already stopped
            else if (key.getCode() == KeyCode.Q && state == GameState.PAUSED) {
                state = GameState.FINISHED;
                gameView.runGame();
            }
            if (key.getCode() == KeyCode.DIGIT1) {
                // go to level 1 - set level to 1
                board.updateFruitsLevelOnUser(Level.ONE, 1);
                changeLevel(Level.ONE, 1, 5);
            }
            if (key.getCode() == KeyCode.DIGIT2) {
                // go to level 2 - set level to 2
                board.updateFruitsLevelOnUser(Level.TWO, 1);
                changeLevel(Level.TWO, 1, 10);
            }
            if (key.getCode() == KeyCode.DIGIT3) {
                // go to level 3 - set level to 3
                board.updateFruitsLevelOnUser(Level.THREE, 1);
                changeLevel(Level.THREE, 1, 15);
            }
        });
        scene.setOnKeyReleased(event -> {
        });
    }

    private boolean move(int dx, int dy) {
        if(dx != 0 || dy != 0) {

            /** MOVE THE SNAKE'S HEAD FIRST **/
            // temporary variables to hold BodyParts
            Snake.BodyPart prev = new Snake.BodyPart(head.getX(), head.getY());
            Snake.BodyPart next = new Snake.BodyPart(head.getX(), head.getY());

            /** THIS IS TO ACHIEVE WRAPPING FEATURE **/
            // move head in X-axis - filled at center of circle - go *2
            head.setX(head.getX()+(dx*Snake.SIZE*2)); //** HERE SIZE DIDN"T **//

            // check if head didn't go beyond screen(>WIDTH or <0), if yes set it on the other side
            if(head.getX() > GameView.WIDTH) {
                head.setX(Snake.SIZE);
            }
            else if(head.getX() < 0) {
                head.setX(GameView.CANVAS_WIDTH-Snake.SIZE);
            }

            // move head in Y-axis
            head.setY(head.getY()+(dy*Snake.SIZE*2)); //** HERE SIZE DIDN"T **//

            // check if head didn't go beyond screen(>HEIGHT or <0), if yes set it on the other side
            if(head.getY() > GameView.CANVAS_HEIGHT) {
                head.setY(Snake.SIZE);
            }
            else if(head.getY() < 0) {
                head.setY(GameView.CANVAS_HEIGHT-Snake.SIZE);
            }

            /** THEN UPDATE THE SNAKE'S BODY **/
            // moving the snake's body, each point gets the position of the one in front
            for(int i = 1; i < snake.getSize(); ++i) {
                next.setX( snake.getBodyPart(i).getX());
                next.setY( snake.getBodyPart(i).getY());

                snake.getBodyPart(i).setX(prev.getX());
                snake.getBodyPart(i).setY(prev.getY());
                prev.setX(next.getX());
                prev.setY(next.getY());
            }
        }
        return true;
    }

    private void startGame(){
        this.setState(GameState.RUNNING);
        new AnimationTimer() {

            long time = 0;
            long countSecs = 0;
            @Override
            public void handle(long now) {
                // when moving up
                if(direction == Dir.UP) {
                    posY = -1; posX = 0;
                }
                // when moving down
                else if(direction == Dir.DOWN) {
                    posY = 1 ; posX = 0;
                }
                // when moving left
                else if(direction == Dir.LEFT) {
                    posY = 0; posX = -1;
                }
                //when moving right
                else if(direction == Dir.RIGHT) {
                    posY = 0; posX = 1;
                }
                // when game finished
                if(state == GameState.FINISHED) {
                    gameView.runGame();
                    stop();
                }

                else if (state == GameState.PAUSED) {
                    gameView.runGame();
                    stop();
                }
                else if (state == GameState.RESET) {
                    gameView.runGame();
                    stop();
                    return;
                }
                // when game is running, make movement
                if(state == GameState.RUNNING) {
                    if (time == 0) {
                        time = now;
                        move(posX, posY);
                        return;
                    }
                    // Track time to show count down
                    if (now - countSecs > 1000000000) {
                        seconds++;
                        countSecs = now;
                    }
                    // Update frames per second
                    if (now - time > 1000000000/speed){
                        time = now;
                        registerKey = move(posX, posY);
                    }
                }
                update();
                gameView.runGame(); // rendering the scene
                userMoves(gameView.getView());
            }
        }.start();

    }

    private void update() {
        if (board.eatFruit() == true) {
            biteFX.play();
        }
        if (board.updateLevel(level)) {
            levelUpFX.play();
        }
        setSound();
        updateLevel();
        board.updateFruit();
        board.updateScore();
        if (board.getScore() != 0) {
            board.updateHighScore();
        }
        board.updateTimer(seconds);
        if (board.eatSelf() == GameState.FINISHED) {
            state = GameState.FINISHED;
            return;
        }
    }

    private void setSound() {
        if(state == GameState.RUNNING) {
            bkMusic.play();
        }
        if(state == GameState.PAUSED || state == GameState.RESET)
            bkMusic.pause();
        if(state == GameState.FINISHED) {
            endGameFX.play();
            bkMusic.stop(); // stop the music when game is finished
        }
    }


    private void updateLevel() {
        // Completed level 1
        if (seconds == 30 && level == Level.ONE) {
            board.updateFruitsLevelOnCompletion(level);
            changeLevel(Level.TWO, 1, 10);
        }
        // Completed level 2
        else if (seconds == 30 && level == Level.TWO) {
            board.updateFruitsLevelOnCompletion(level);
            changeLevel(Level.THREE, 1, 15);
        }
        else if (level == Level.THREE) {
            speed = 15;
        }

    }

    public void changeLevel(Level newLevel, int time, int newSpeed){
        level = newLevel;
        seconds = time;
        speed = newSpeed;
        if (board.eatFruit() == true) {
            biteFX.play();
        }
        /** CHECK IF BODY IS ON FRUIT WHEN SWITCHING LEVELS **/
        /**MAYBE INSTEAD CHECK TO SEE IF BODY IS ON FRUIT BEFORE PLACING FRUIT!!!! **/
        if (board.bodyOnFruit() == true) {
            biteFX.play();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public void resetGame() {
        direction = Dir.UP;
        seconds = 0;
        speed = 5;
    }

    /** Return the actual state of game for the Model and View classes */
    public static GameState getState() {
        return state;
    }
    /** Return the actual state of game for the Model and View classes */
    public void setState(GameState state) {
        GameController.state = state;
    }


    public static Level getLevel() {
        return level;
    }

}
