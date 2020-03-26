package Application;

import Models.Menu;
import Models.User;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;
import java.net.ConnectException;
import java.util.TreeMap;

import static Application.RestaurantApplication.stageManager;
import static Helpers.ServerRequests.httpClientLongPolling;

public class LoginManager {
    private LoginService loginService = new LoginService();
    private RegisterService registerService = new RegisterService();
    private User loggedUser = new User();
    public static IntegerProperty userId = new SimpleIntegerProperty();
    public TreeMap<String, Menu> userMenu = new TreeMap<>();

    private LoginManager(){
        userId.bind(loggedUser.getId());

        loginService.setOnSucceeded(eventSuccess -> onSuccessfulService(loginService));
        loginService.setOnFailed(eventFail -> updateError(loginService));

        registerService.setOnSucceeded(eventSuccess -> onSuccessfulService(registerService));
        registerService.setOnFailed(eventFail -> updateError(loginService));
    }
    static LoginManager initialize(){
        return new LoginManager();
    }

    public void bindLoginFields(StringProperty username, StringProperty password){
        loginService.username.bind(username);
        loginService.password.bind(password);
    }

    public void bindRegisterFields(StringProperty username, StringProperty password, StringProperty repeatPassword){
        registerService.username.bind(username);
        registerService.password.bind(password);
        registerService.repeatPassword.bind(repeatPassword);
    }

    private void updateError(Service service) {
        Throwable exception = service.getException();
        String exceptionMessage = exception.getMessage();

        try {
            throw exception;
        } catch (ConnectException e) {
            exceptionMessage = "No connection to the server.";
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        stageManager.currentController.resetStage();
        stageManager.showAlert(exceptionMessage);
        service.reset();
    }

    public void login(){
        loginService.start();
    }
    public void register(){
        registerService.start();
    }

    private void onSuccessfulService(Service service) {
        setUser((User) service.getValue());
        service.reset();

        stageManager.changeToOwner();
    }

    public void logout(){
        //Todo: remove close + reset user
        try {
            httpClientLongPolling.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpClientLongPolling = HttpClients.createDefault();

        stageManager.changeToOwner();
    }


    public void setUser(User user) {
        loggedUser.getRestaurant().getMenu().forEach(menu ->
                userMenu.put(menu.getName().toLowerCase(), menu));

        loggedUser.setId(user.getId().get());
        loggedUser.setUsername(user.getUsername().get());
        loggedUser.setUsername(user.getFirstName().get());
        loggedUser.setLastName(user.getLastName().get());
        loggedUser.setAge(Integer.valueOf(user.getAge().get()));
        loggedUser.setCountry(user.getCountry().get());
        loggedUser.setRole(user.getRole().get());
        loggedUser.setRestaurant(user.getRestaurant());
        loggedUser.setProfilePicture(user.getProfilePicture().get());
    }
}