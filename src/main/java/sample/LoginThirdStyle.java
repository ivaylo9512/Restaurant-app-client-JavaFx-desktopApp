package sample;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginThirdStyle extends LoginFirstStyle {
    static Alert alert;
    static Stage stage;
    static Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

    static {
        try {
            initializeStage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initializeStage() throws IOException{

        FXMLLoader loader = new FXMLLoader(LoggedSecondStyle.class.getResource("/FXML/login-third.fxml"));
        Pane root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(LoggedSecondStyle.class.getResource("/css/login-third.css").toString());
        scene.setFill(Color.TRANSPARENT);
        ControllerLoginThirdStyle controller = loader.getController();

        stage = createStage(scene, controller);
        alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(stage);
        alert.initStyle(StageStyle.TRANSPARENT);
        DialogPane dialog = alert.getDialogPane();
        dialog.setGraphic(null);
        dialog.getStyleClass().add("alert-box");
    }

    private static Stage createStage(Scene scene, ControllerLoginThirdStyle controller) {

        Stage stage = new Stage();
        stage.showingProperty().addListener((observable, oldValue, isShowing) -> {
            if(!isShowing){
                controller.resetStage();
            }else{
                controller.setStage();
            }
        });
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        return stage;
    }

}
