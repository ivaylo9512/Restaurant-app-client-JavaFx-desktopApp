package sample;

import Application.StageManager;
import javafx.concurrent.Service;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

import static Application.RestaurantApplication.registerService;
import static Application.RestaurantApplication.loginService;

public class ControllerLoginSecondStyle implements Controller{
    @FXML TextField username, password, regUsername, regPassword, regRepeatPassword;
    @FXML AnchorPane root, loginPane, loginFields, registerFields, nextRegisterFields, styleButtons;
    @FXML Button actionBtn;
    @FXML ImageView actionBtnImage;
    @FXML Image loginImage;


    private Pane currentMenu;
    private boolean loading;

    @FXML
    public void initialize(){
        currentMenu = loginFields;
    }

    @FXML
    public void login(Event event){
        if(!KeyEvent.KEY_RELEASED.equals(event.getEventType()) || ((KeyEvent) event).getCode().equals(KeyCode.ENTER)) {
            username.setDisable(true);
            password.setDisable(true);
            root.setCursor(Cursor.WAIT);

            loading = true;
            loginService.start();
        }

    }
    @FXML
    public void register(Event event){
        if(!KeyEvent.KEY_RELEASED.equals(event.getEventType()) || ((KeyEvent) event).getCode().equals(KeyCode.ENTER)) {
            regUsername.setDisable(true);
            regPassword.setDisable(true);
            regRepeatPassword.setDisable(true);
            root.setCursor(Cursor.WAIT);

            loading = true;
            registerService.start();
        }
    }


    public void setStage(){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        loginPane.setLayoutY((primaryScreenBounds.getHeight() - loginPane.getHeight()) / 2);
        loginPane.setLayoutX((primaryScreenBounds.getWidth() - loginPane.getWidth()) / 2);

        loginService.bind(username.textProperty(), password.textProperty());
        registerService.bind(regUsername.textProperty(), regPassword.textProperty(), regRepeatPassword.textProperty());
    }

    public void resetStage(){
        if(loading){
            username.setDisable(false);
            password.setDisable(false);
            regUsername.setDisable(false);
            regPassword.setDisable(false);
            regRepeatPassword.setDisable(false);
            root.setCursor(Cursor.DEFAULT);
        }else {
            loginFields.setDisable(false);
            root.setCursor(Cursor.DEFAULT);

            showMenu(loginFields);

            username.setDisable(false);
            password.setDisable(false);
            regUsername.setDisable(false);
            regPassword.setDisable(false);
            regRepeatPassword.setDisable(false);

            loading = false;
            resetFields();
        }
    }

    private void resetFields() {
        username.setText(null);
        password.setText(null);
        regUsername.setDisable(false);
        regPassword.setDisable(false);
        regRepeatPassword.setDisable(false);
        regUsername.setText(null);
        regPassword.setText(null);
        regRepeatPassword.setText(null);
    }

    @FXML
    public void showLoginFields(){
        resetFields();
        showMenu(loginFields);
        actionBtn.setOnMouseClicked(this::login);
    }
    @FXML
    public void showRegisterFields(){
        resetFields();
        showMenu(registerFields);
        actionBtn.setOnMouseClicked(this::showNextRegisterFields);

    }
    @FXML
    public void showNextRegisterFields(Event event){
        if(!KeyEvent.KEY_RELEASED.equals(event.getEventType()) || ((KeyEvent) event).getCode().equals(KeyCode.ENTER)) {
            showMenu(nextRegisterFields);
            actionBtn.setOnMouseClicked(this::register);
        }
    }
    @FXML void showStyleButtons(){
        showMenu(styleButtons);
        actionBtn.setOnMouseClicked(event -> close());
    }

    private void showMenu(Pane requestedMenu){
        if(currentMenu != null){
            currentMenu.setOpacity(0);
            currentMenu.setDisable(true);
        }
        if(requestedMenu.equals(styleButtons)){
            actionBtnImage.setImage(null);
            actionBtn.setText("X");
        }else{
            actionBtnImage.setImage(loginImage);
            actionBtn.setText(null);
        }
        requestedMenu.setOpacity(1);
        requestedMenu.setDisable(false);
        currentMenu = requestedMenu;
    }

    @FXML
    public void showLoginThirdStyle(){
        StageManager.changeStage(StageManager.thirdLoginStage);
    }
    @FXML
    public void showLoginFirstStyle(){
        StageManager.changeStage(StageManager.firstLoginStage);
    }

    @FXML
    public void close(){
        StageManager.currentStage.close();
    }
}
