package sample;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoggedSecondStyle extends LoginFirstStyle{

    static Alert alert;
    public static Stage stage;
    public static ControllerLoggedSecondStyle controller;

    static{
        try {
            initializeStage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initializeStage() throws IOException {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        FXMLLoader loader = new FXMLLoader(LoggedSecondStyle.class.getResource("/FXML/logged-second.fxml"));
        Pane root = loader.load();

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(LoggedSecondStyle.class.getResource("/css/logged-second.css").toString());

        stage = new Stage();
        ControllerLoggedSecondStyle controller = loader.getController();
        stage.showingProperty().addListener((observable, oldValue, isShowing) -> {
            if(isShowing) {
                try {
                    controller.setStage();
                } catch (Exception e) {
                    controller.resetStage();
                    stage.close();
                    LoginFirstStyle.stage.show();

                    DialogPane dialog = LoginFirstStyle.alert.getDialogPane();
                    dialog.setContentText(e.getMessage());
                    LoginFirstStyle.alert.showAndWait();
                }
            }else{
                controller.resetStage();
            }
        });

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());

        AnchorPane contentRoot = (AnchorPane) root.getChildren().get(0);
        contentRoot.setLayoutY((primaryScreenBounds.getHeight() - contentRoot.getHeight()) / 2);
        contentRoot.setLayoutX((primaryScreenBounds.getWidth() - contentRoot.getWidth()) / 2);

        AnchorPane menuRoot = (AnchorPane) root.getChildren().get(1);
        menuRoot.setLayoutX((primaryScreenBounds.getWidth() - menuRoot.getWidth()) / 2);
        menuRoot.setLayoutY(contentRoot.getLayoutY() - 60);

        alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(stage);
        alert.initStyle(StageStyle.TRANSPARENT);
        DialogPane dialog = alert.getDialogPane();
        dialog.setGraphic(null);
        dialog.getStyleClass().add("alert-box");
    }
}
