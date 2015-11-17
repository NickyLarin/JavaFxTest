package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader mainFxmlLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root = mainFxmlLoader.load();

            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.setTitle("Крестики/Нолики");
            primaryStage.setResizable(false);
            Scene scene = new Scene(root);
            scene.getStylesheets().add("view/Main.css");
            scene.setFill(Color.TRANSPARENT);
            primaryStage.setScene(scene);
            primaryStage.setOpacity(0.0);
            primaryStage.setOnShown(event -> View.getInstance().playStartAnimation(primaryStage));
            primaryStage.show();
        }
        catch (IllegalStateException e) {
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


    public static void main(String[] args) {
            Application.launch(args);
    }
}
