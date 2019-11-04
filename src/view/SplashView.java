package view;

import controller.SplashController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;


public class SplashView extends BorderPane {

    /** Size of game board **/
    public final static int WIDTH = 1280;
    public final static int HEIGHT = 800;


    private Scene scene;
    private Stage stage;

    Button btn1 = new Button("Level 1");
    Button btn2 = new Button("Level 2");
    Button btn3 = new Button("Level 3");

    public SplashView(final SplashController splashCtrl) {
        // Get same from the Main file (shared stage)
        stage = splashCtrl.getPrimaryStage();
        stage.setTitle("Snake | Home");

        btn1.setOnAction(splashCtrl);
        btn2.setOnAction(splashCtrl);
        btn3.setOnAction(splashCtrl);
        displaySplashScreen();
    }

    private void displaySplashScreen() {
        Text myInfo = new Text("Created by: Mary Wang | User ID: mq5wang");
        myInfo.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        myInfo.setFill(Color.ROYALBLUE);

        VBox vLayout1 = new VBox();
        vLayout1.setAlignment(Pos.CENTER);
        vLayout1.getChildren().addAll(myInfo);

        Text snakeGameTitle = new Text("Snake!");
        snakeGameTitle.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 50));

        Text selectLevelText = new Text("Select a level to begin:");
        selectLevelText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        selectLevelText.setFill(Color.LIGHTCORAL);

        VBox vLayout2 = new VBox();
        vLayout2.setSpacing(20);
        vLayout2.setAlignment(Pos.CENTER);
        vLayout2.getChildren().addAll(snakeGameTitle, selectLevelText,  btn1, btn2, btn3);

        Text instructions = new Text("Instructions: ");
        instructions.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        instructions.setFill(Color.ROYALBLUE);
        Text line1 = new Text("Use corresponding arrow keys to move the snake up, down, left or right");
        Text lineNums = new Text("Use numbers 1, 2 and 3 to navigate between levels while in game");
        Text line2 = new Text("Press P to pause and resume the game");
        Text line3 = new Text("Press R to reset and go to the home screen");
        Text line4 = new Text("Press Q to end the game and see score");
        ArrayList<Text> insList = new ArrayList<>();
        insList.add(line1); insList.add(lineNums); insList.add(line2); insList.add(line3); insList.add(line4);

        for (Text ins : insList) {
            ins.setFill(Color.SKYBLUE);
            ins.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));
        }

        VBox vLayout3 = new VBox();
        vLayout3.setAlignment(Pos.CENTER);
        vLayout3.getChildren().addAll(instructions, line1,lineNums, line2, line3, line4);

        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(80, 20, 50, 20));

        pane.setTop(vLayout1);
        pane.setCenter(vLayout2);
        pane.setBottom(vLayout3);

        scene = new Scene(pane, WIDTH, HEIGHT);
        pane.setBackground(new Background(new BackgroundFill(Color.AZURE, null, null)));
        stage.setScene(scene);
        stage.show();

    }

    public Button getButton1() {
        return btn1;
    }

    public Button getButton2() {
        return btn2;
    }

    public Button getButton3() {
        return btn3;
    }


}