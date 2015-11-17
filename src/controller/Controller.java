package controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Model;
import view.View;
import view.gameOverDialog.GameOverDialog;

public class Controller {
    private Model model = Model.getInstance();
    private View view = View.getInstance();

    private double dragOffsetX;
    private double dragOffsetY;
    private double sceneOffsetX;
    private double sceneOffsetY;

    @FXML
    private GridPane gridPane;

    @FXML
    private void handleButtonClick(ActionEvent event) {
        Button btn = (Button)event.getSource();
        String[] tmp = btn.getId().substring(6).split("_");
        int x = Integer.parseInt(tmp[0]);
        int y = Integer.parseInt(tmp[1]);
        int a = model.setCell(x, y);
        if(a == 1) {
            view.playTurnSound();
            view.drawButtonClick(btn, false);
        }
        else if(a == 2) {
            view.playTurnSound();
            view.drawButtonClick(btn, true);
        }
        int winner = model.getWinner();
        if(winner != 0){
            gameEnd(winner);
        }
    }

    @FXML
    private void handleExit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void handleMousePressed(MouseEvent event) {
        dragOffsetX = event.getScreenX() - gridPane.getScene().getWindow().getX();
        dragOffsetY = event.getScreenY() - gridPane.getScene().getWindow().getY();
    }

    @FXML
    private void handleMouseDragged(MouseEvent event) {
        gridPane.getScene().getWindow().setX(event.getScreenX() - dragOffsetX);
        gridPane.getScene().getWindow().setY(event.getScreenY() - dragOffsetY);
    }

    private void gameEnd(int winner) {
        GameOverDialog gameOverDialog = new GameOverDialog(winner, (Stage)gridPane.getScene().getWindow());
        if (gameOverDialog.open()) {
            clearAllButtons();
            model.reset();
        } else {
            Platform.exit();
        }
    }

    private void clearAllButtons() {
        ObservableList list = gridPane.getChildrenUnmodifiable();
        for(Object obj : list) {
            if (obj instanceof Button) {
                ((Button) obj).setGraphic(new ImageView());
            }
        }
    }

    /*
    private void gameEnd(int winner){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Игра окончена");
        if(winner == 1){
            alert.setContentText("Победили крестики\n" +
                    "Повторить?");
        }
        else if(winner == 2){
            alert.setContentText("Победили нолики\n" +
                    "Повторить?");
        }
        else{
            alert.setContentText("Ничья\n" +
                    "Повторить?");
        }

        alert.setHeaderText("");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);


        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()==ButtonType.YES){
            clearAllButtons();
            model.reset();
        }
        else{
            Stage stage = (Stage)button1_1.getScene().getWindow();
            stage.close();
        }
    }
    */
}
