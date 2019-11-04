package view;

import controller.GameController;
import controller.SplashController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

import java.util.ArrayList;

public class GameView extends Parent {
    private final Button button = new Button("ButtonB");

    public final static int WIDTH = 1280;
    public final static int HEIGHT = 800;

    public final static int CANVAS_WIDTH = WIDTH;
    public final static int CANVAS_HEIGHT = HEIGHT-InfoBar.INFO_HEIGHT;

    public final static String SCENE_COLOR = "AZURE";
    private Snake snake;
    private Scene scene;
    private Stage stage;

    private Level level;

    private GameState state;
    private ArrayList<Fruit> fruits;

    private Board board;
    private Group g;
    private Pane canvas;
    private GridPane grid;
    private StackPane stack;


    public GameView(final GameController gameCtrl) {
        board = new Board(gameCtrl);
        snake = board.getSnake();
        fruits = board.getFruits();
        level = board.getLevel();

        stage = gameCtrl.getPrimaryStage();
        stage.setTitle("Snake");

        canvas = new Pane();
        canvas.setStyle("-fx-background-color: "+SCENE_COLOR);
        canvas.setPrefSize(CANVAS_WIDTH, CANVAS_HEIGHT);

        stack = new StackPane();
        grid = new GridPane();

        g = new Group();
        scene = new Scene(g, WIDTH, HEIGHT);
        scene.setFill(Color.web(SCENE_COLOR));

        runGame();
    }

    public void runGame() {
        this.state = GameController.getState(); // get the actual game state
        this.level = GameController.getLevel(); // get the actual level of the game
        switch(state) { // checks for actual game state

            case RESET:
                whenReset();
                break;
            case RUNNING:
                whenRunning();
                break;
            case PAUSED:
                whenPaused();
                break;
            case FINISHED:
                whenFinished();
                break;
            default:
                break;
        }
    }

    private void whenReset() {
        SplashController splashScreen = new SplashController(stage);
        stage = splashScreen.getPrimaryStage();
        GameController.setPrimaryStage(stage);
    }


    private void whenRunning() {
        grid.getChildren().clear();
        canvas.getChildren().clear();
        stack = board.getInfoBar().getStack(); // get the Info Bar created to keep score

        int fruitX, fruitY, snakeY, snakeX;

        // add snake's head to canvas
        Circle c = new Circle(snake.getHead().getX() , snake.getHead().getY(), Snake.SIZE);
        c.setFill(Snake.HEAD_COLOR);
        canvas.getChildren().add(c);

        // add snake's body to canvas
        for(int i = 1; i < snake.getSize(); ++i) {
            snakeX = snake.getBodyPart(i).getX();
            snakeY = snake.getBodyPart(i).getY();
            c = new Circle(snakeX , snakeY, Snake.SIZE);
            c.setFill(Snake.BODY_COLOR);
            canvas.getChildren().add(c);
        }

        // loading fruits to canvas
        for(int i = 0; i < fruits.size(); ++i) {
            fruitX = fruits.get(i).getX();
            fruitY = fruits.get(i).getY();
            c = new Circle(fruitX , fruitY, Snake.SIZE);
            c.setFill(Fruit.FRUIT_COLOR);
            canvas.getChildren().add(c);
        }
        grid.add(stack, 0, 0);
        grid.add(canvas, 0, 1);

        scene.setRoot(grid);
        stage.setScene(scene);

    }
    private void whenPaused() {
        VBox vLayout = new VBox();
        Text pauseText, resumeText, quitText, resetText;

        pauseText = new Text(WIDTH/2 - 190, HEIGHT/2 - 30, "Game Paused");
        pauseText.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 40));
        pauseText.setFill(Color.LIGHTCORAL);

        resumeText = new Text(WIDTH/2 - 190, HEIGHT/2 + 30, "Press P to resume");
        resumeText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 20));
        resumeText.setFill(Color.SKYBLUE);

        resetText = new Text(WIDTH/2 - 190, HEIGHT/2 + 30, "Press R to go back to the home screen");
        resetText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 20));
        resetText.setFill(Color.SKYBLUE);

        quitText = new Text(WIDTH/2 - 190, HEIGHT/2 + 30, "Press Q to end game and see score");
        quitText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 20));
        quitText.setFill(Color.SKYBLUE);

        vLayout.setSpacing(15);
        vLayout.setAlignment(Pos.CENTER);
        vLayout.setBackground(new Background(new BackgroundFill(Color.AZURE, null, null)));
        vLayout.getChildren().addAll(pauseText, resumeText, resetText, quitText);
        scene.setRoot(vLayout);
        stage.setScene(scene);
    }

    private void whenFinished() {
        VBox vLayout = new VBox();
        Text gameOver = new Text(WIDTH/2 - 220, HEIGHT/2 - 60, "Game Over");
        gameOver.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 40));
        gameOver.setFill(Color.LIGHTCORAL);

        Text scoreText = new Text(WIDTH/2 - 170, HEIGHT/2 + 20, "Highscore: " + board.getHighScore());
        scoreText.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 30));

        Text resetText = new Text(WIDTH/2 - 160, HEIGHT/2 +100, "Press R to go back to the home screen");
        resetText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 20));
        resetText.setFill(Color.SKYBLUE);

        Text exitText = new Text(WIDTH/2 - 130, HEIGHT/2 + 130 , "Press ESC to exit the game");
        exitText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 20));
        exitText.setFill(Color.SKYBLUE);
        vLayout.setSpacing(20);
        vLayout.setAlignment(Pos.CENTER);
        vLayout.setBackground(new Background(new BackgroundFill(Color.AZURE, null, null)));
        vLayout.getChildren().addAll(gameOver, scoreText, resetText, exitText);
        scene.setFill(Color.web(SCENE_COLOR));
        scene.setRoot(vLayout);
        stage.setScene(scene);

    }

    /** pass the snake object **/
    public Snake getSnake() {
        return snake;
    }

    public Board getBoard() {
        return board;
    }

    public Scene getView() {
        return stage.getScene();
    }

}