import javafx.application.Application;
import javafx.stage.Stage;
import controller.*;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        SplashController splashScreen = new SplashController(stage);
        stage = splashScreen.getPrimaryStage();
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}


