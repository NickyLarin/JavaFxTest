package view;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * Created by Nicky on 02.11.2015.
 */
public class View {
    private static View instance;

    private final String pathToTurnSound = "sounds/turn.wav";
    private AudioClip turnSound;

    private final String pathToWinSound = "sounds/win1.wav";
    private AudioClip winSound;

    private final String pathToX = "/view/images/x.png";
    private final String pathToO = "/view/images/o.png";
    private Image imageX;
    private Image imageO;

    private View() {
        try {
            imageX = new Image(pathToX);
            imageO = new Image(pathToO);
            turnSound = new AudioClip(getClass().getResource(pathToTurnSound).toString());
            winSound = new AudioClip(getClass().getResource(pathToWinSound).toString());
        }
        catch (IllegalArgumentException | NullPointerException e) {
            System.err.println(e.getMessage());
            Platform.exit();
        }
        catch (Exception e) {
            System.err.println(e.getClass());
            for (StackTraceElement element :e.getStackTrace()) {
                System.err.println(element);
            }
            Platform.exit();
        }
    }

    public static synchronized View getInstance() {
        if (instance == null) {
            instance = new View();
        }
        return instance;
    }

    private void playTurnAnimation(ImageView imageView) {
        double duration = 0.4;

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(duration), imageView);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(duration), imageView);
        scaleTransition.setFromX(0.0);
        scaleTransition.setFromY(0.0);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);

        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(duration), imageView);
        rotateTransition.setFromAngle(0.0);
        rotateTransition.setToAngle(360.0);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(fadeTransition, scaleTransition);
        parallelTransition.play();
    }

    public void drawButtonClick(Button button, boolean zeroesTurn) {
        ImageView imageView = new ImageView(imageX);
        if (zeroesTurn) {
            imageView = new ImageView(imageO);
        }
        button.setGraphic(imageView);
        playTurnAnimation(imageView);

    }

    public void playTurnSound() {
        turnSound.play();
    }

    public void playWinSound() {
        winSound.play();
    }

    public void playStartAnimation(Stage stage) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(stage.opacityProperty(), 0.0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(stage.opacityProperty(), 1.0)));
        timeline.play();
    }
}
