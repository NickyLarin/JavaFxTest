package view.gameOverDialog;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.View;

/**
 * Created by Nicky on 14.11.2015.
 */
public class GameOverDialog {
    private Stage stage;
    private boolean result;

    public GameOverDialog(int winner, final Stage parentStage) {
        stage = new Stage();
        Text gameOverText = new Text("Игра окончена!\n");
        gameOverText.setId("gameOverText");
        Text winnerIsText = new Text();
        winnerIsText.setId("winnerIsText");
        switch (winner) {
            case 1:
                winnerIsText.setText("Победили крестики!");
                break;
            case 2:
                winnerIsText.setText("Победили нолики!");
                break;
            default:
                winnerIsText.setText("Ничья.");
        }

        TextFlow dialogText = new TextFlow(gameOverText, winnerIsText);
        dialogText.setId("dialogText");
        dialogText.setTextAlignment(TextAlignment.CENTER);

        Button retryButton = new Button("Повторить");
        retryButton.setId("retryButton");
        retryButton.setOnAction(actionEvent -> {
            result = true;
            stage.close();
        });

        Button cancelButton = new Button("Хватит");
        cancelButton.setId("cancelButton");
        cancelButton.setOnAction(actionEvent -> {
            result = false;
            stage.close();
        });

        HBox buttonsHbox = new HBox(retryButton, cancelButton);
        buttonsHbox.setId("buttonsHbox");

        VBox vBox = new VBox(dialogText, buttonsHbox);
        vBox.setId("vBox");

        Scene dialogScene = new Scene(vBox);
        dialogScene.setFill(Color.TRANSPARENT);
        dialogScene.getStylesheets().add("view/gameOverDialog/GameOverDialog.css");

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(dialogScene);
        stage.setOpacity(0.0);
        stage.setOnShown(event -> {
            stage.setX(parentStage.getX() + (parentStage.getWidth() / 2) - (stage.getWidth() / 2));
            stage.setY(parentStage.getY() + (parentStage.getHeight() / 2) - (stage.getHeight() / 2));
            View.getInstance().playStartAnimation(stage);
            View.getInstance().playWinSound();
        });
    }

    public boolean open() {
        stage.showAndWait();
        return result;
    }
}
