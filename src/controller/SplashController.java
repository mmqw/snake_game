package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import model.GameState;
import model.Level;
import view.SplashView;

public class SplashController implements EventHandler {

    private Stage primaryStage;
    private final SplashView splashView;

    public SplashController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        splashView = new SplashView(this);
    }

    @Override
    public void handle(Event event) {
        final Object source = event.getSource();

        if (source.equals(splashView.getButton1())) {
            final GameController gameCtrl = new GameController(primaryStage, Level.ONE);
            /** Set game state to start running **/
            gameCtrl.setState(GameState.RUNNING);
            primaryStage = gameCtrl.getPrimaryStage();
        }

        if (source.equals(splashView.getButton2())) {
            final GameController gameCtrl = new GameController(primaryStage, Level.TWO);
            /** Set game state to start running **/
            gameCtrl.setState(GameState.RUNNING);
            primaryStage = gameCtrl.getPrimaryStage();
        }

        if (source.equals(splashView.getButton3())) {
            final GameController gameCtrl = new GameController(primaryStage, Level.THREE);
            /** Set game state to start running **/
            gameCtrl.setState(GameState.RUNNING);
            primaryStage = gameCtrl.getPrimaryStage();
        }

    }
    public Stage getPrimaryStage() {
        return primaryStage;
    }

}